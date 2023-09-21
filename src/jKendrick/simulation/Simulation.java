package jKendrick.simulation;

import java.io.IOException;

import jKendrick.IHM.Visualization;
import jKendrick.core.ISolver;

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
			try {
				v.getChart(solver, title, "time", "population");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
