package jKendrick.scenario;

import java.io.IOException;

import jKendrick.Visualization;

public class Simulation {
	private ISolver solver;
	private Visualization v;
	private String title;
	
	public Simulation(ISolver solver,Visualization v, String title) {
		this.solver=solver;
		this.v=v;
		this.title=title;
	}
	
	public void simulate() {
		solver.solve();
		double[][][] result=solver.getResult();
		if(result.length==1) {
			try {
				v.deterministicChart(result[0], solver.getStep(), solver.getEnd(), solver.getLabels(), title+" Deterministic", "time", "population");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			double [][] medianPath=solver.getMedianPath();
			try {
				v.stochasticChart(solver.getStep(),result, medianPath, solver.getLabels(), title+" Stochastic", "time","population");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}

}
