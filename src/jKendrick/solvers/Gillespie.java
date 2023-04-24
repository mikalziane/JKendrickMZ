package jKendrick.solvers;

import java.util.Map;

public class Gillespie {
	private int timeLimit;
	private int nbCycle;
	private int nbSteps;
	private Map<String,Integer> nbIndiv;
	private double[][][] result;//[cycle][step][compartment]
	
	public Gillespie(int timeLimit, int nbCycle, int nbStep, Map<String,Integer>nbIndiv) {
		this.timeLimit=timeLimit;
		this.nbCycle=nbCycle;
		this.nbSteps=nbStep;
		this.nbIndiv=nbIndiv;
		this.result=initResult();
		
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

}
