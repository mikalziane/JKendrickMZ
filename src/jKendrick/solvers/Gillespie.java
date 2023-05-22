package jKendrick.solvers;


import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import jKendrick.events.IEvent;
import jKendrick.tools.GeneralizedRW;


public class Gillespie {
	private int nbCycle;
	private int nbSteps;
	private Map<String,Integer> nbIndiv;
	private double[][][] result;//[cycle][step][compartment]
	private String[] compartments;
	private IEvent[] events;
	private Random random;
	
	public Gillespie(int nbCycle, int nbStep, Map<String,Integer>nbIndiv, IEvent[] events) {
		assert nbCycle>0;
		assert nbStep>0;
		assert nbIndiv.size()>0;
		assert events.length>0;
		this.nbCycle=nbCycle;
		this.nbSteps=nbStep;
		this.nbIndiv=nbIndiv;
		this.compartments=nbIndiv.keySet().toArray((new String[nbIndiv.size()]));
		initValues();
		this.random=new Random();
		this.events=events;
	}
	
	public void initValues(){
		double result[][][]=new double[nbCycle][nbSteps][nbIndiv.size()+1];
		int j=0;
		for(int i=0;i<nbCycle;++i) {
			for(Map.Entry<String, Integer> entry : nbIndiv.entrySet()) {
				result[i][0][j]=(double)entry.getValue();
				++j;
			}
			result[i][0][j]=0.;
			j=0;
		}
		this.result=result;
	}
	
	public double[] getRates(double[] population) {
		assert population.length>0;
		double[] r=new double[events.length];
		for(int i=0;i<r.length;++i) {
			r[i]=events[i].getRate(compartments,population);
		}
		return r;
	}
	
	public double[] getInitialPopulation() {
		double[] pop=new double[nbIndiv.size()];
		int i=0;
		for(Map.Entry<String, Integer> entry : nbIndiv.entrySet()) {
			pop[i]=(double)entry.getValue();
			++i;
		}
		return pop;
	}
	
	public double getR() {
		double[] rates=getRates(getInitialPopulation());
		double r=0;
		for(int i=0;i<rates.length;++i) {
			r+=rates[i];
		}
		return r;
	}
	
	public void solve(){
		double r=getR();
		int timeRow=nbIndiv.size();
		for(int i=0;i<nbCycle;++i) {
			for(int j=1;j<nbSteps;++j) {
				double rand1=random.nextDouble();
				double tau=1/r*Math.log(1/rand1);
				GeneralizedRW rw=new GeneralizedRW(getRates(result[i][j-1]),0.0000001);
				int currentEvent=rw.getEvent();
				if(currentEvent!=-1) {
					result[i][j]=events[currentEvent].action(compartments, result[i][j-1]);
				}
				else {
					result[i][j]=result[i][j-1];
				}
				result[i][j][timeRow]=result[i][j-1][timeRow]+tau;
			}
		}
	}
	
	public double[][] getAverage(){
		double[][] averages=new double[nbSteps][nbIndiv.size()+1];
		double x=0.;
		for(int i=0;i<nbSteps;++i) {
			for(int j=0;j<nbIndiv.size()+1;++j) {
				x=0.;
				for(int k=0;k<nbCycle;k++) {
					x+=result[k][i][j];
				}
				x=x/(double)nbCycle;
				averages[i][j]=x;
			}
		}
		return averages;
	}
	
	public double getAverageStep() {
		double[][] averages=getAverage();
		double avStep=0.;
		for(int i=1;i<nbSteps;++i) {
			avStep+=averages[i][(averages[0].length)-1]-averages[i-1][(averages[0].length)-1];
		}
		avStep=avStep/nbSteps;
		return avStep;
	}
	
	public double[][] getValues(){
		double[][] averages=getAverage();
		double[][] values=new double[nbIndiv.size()][nbSteps];
		for(int i=0;i<nbSteps;++i) {
			for(int j=0;j<nbIndiv.size();++j) {
				values[j][i]=averages[i][j];
			}
		}
		return values;
	}
	
	public double getValue(int cycle, int step, int compartment) {
		return result[cycle][step][compartment];
	}
	
	public double[][][] getResult(){
		double r[][][]=new double[nbCycle][nbSteps][nbIndiv.size()+1];
		for(int i=0;i<nbCycle;++i) {
			for(int j=0;j<nbSteps;++j) {
				for(int k=0;k<=nbIndiv.size();++k) {
					r[i][j][k]=result[i][j][k];
				}
			}
		}
		return r;
	}
	
	public double[][] getMedianPath(){
		double[][] median=new double[nbSteps][nbIndiv.size()+1];
		double[][][] r=getResult();
		int cycle=getMedCycle();
		median=r[cycle];
		return median;
	}
	
	public int getMedCycle() {
		int min=0;
		double[][][] scores=getScores();
		for(int i=0;i<nbCycle;++i) {
			if(getCycleScore(scores, min)>getCycleScore(scores, i)) {
				min=i;
			}
		}
		return min;
		
	}
	
	public double getCycleScore(double[][][] scores, int cycle) {
		double[] cycleScores=getCycleScores(scores, cycle);
		double score=0.;
		for(int i=0;i<cycleScores.length;++i) {
			score+=cycleScores[i];
		}
		score=score/cycleScores.length;
		return score;
	}
	
	public double[] getCycleScores(double[][][] scores, int cycle){
		int nbScores=nbSteps*(nbIndiv.size()+1);
		double[] cycleScores=new double[nbScores];
		int k=0;
		for(int i=0;i<nbSteps;++i) {
			for(int j=0;j<=nbIndiv.size();++j) {
				cycleScores[k]=scores[i][j][cycle];
			}
		}
		return cycleScores;		
	}
	
	public double[][][] getScores(){
		double[][][] scores=new double[nbSteps][nbIndiv.size()+1][nbCycle];
		for(int i=0;i<nbSteps;++i) {
			for(int j=0;j<=nbIndiv.size();++j) {
				scores[i][j]=getRanks(i, j);
			}
		}
		return scores;
	}
	
	
	
	public double[] getRanks(int step, int compartment) {
		double[][][] r=getResult();
		double[] tab=new double[nbCycle];
		for(int i=0;i<nbCycle;++i) {
			tab[i]=r[i][step][compartment];
		}
		double[] sorted=sort(tab)[1];
		double[] ranks=new double[nbCycle];
		for(int i=0;i<nbCycle;++i) {
			for(int j=0;j<nbCycle;++j) {
				if(sorted[j]==i) {
					ranks[i]=j;
				}
			}
		}
		return ranks;
	}
	
	public double[][] prepareToSort(double[] t){
		double[][] p=new double[2][t.length];
		for(int i=0;i<t.length;++i) {
			p[0][i]=(double)i;
			p[1][i]=t[i];
		}
		return p;
	}
	
	public double[][] sort(double[] tab) {
		double[][] t=prepareToSort(tab);
		if(t.length>0) {
			sort(t,0,t.length-1);
		}
		return t;
	}
	
	public void sort(double[][] t, int start, int end) {
		if(start != end) {
			int mid=(start+end)/2;
			sort(t,start,mid);
			sort(t,mid+1,end);
			merge(t,start,mid,end);
		}
	}
	
	public void merge(double[][] t, int start, int mid, int end) {
		int start2=mid+1;
		double[][] tab1=new double[2][mid-start+1];
		for(int i=start; i<=mid;++i) {
			tab1[0][i-start]=t[0][i];
			tab1[1][i-start]=t[1][i];
		}
		int c1=start;
		int c2=start2;
		for(int i=start;i<=end;++i) {
			if(c1==start2) {
				break;
			}
			else if((c2==end+1)||(tab1[1][c1-start]<t[1][c2])) {
				t[0][i]=tab1[0][c1-start];
				t[1][i]=tab1[1][c1-start];
				++c1;
			}
			else {
				t[0][i]=t[0][c2];
				t[1][i]=t[1][c2];
				++c2;
			}
		}
	}
}
