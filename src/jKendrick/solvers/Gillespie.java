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
		
		this.result=initResult();
		this.random=new Random();
		this.events=events;
	}
	
	public double[][][] initResult(){
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
		return result;
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
	
	public double[][][] solve(){
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
		return result;
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
}
