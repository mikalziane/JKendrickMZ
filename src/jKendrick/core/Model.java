package jKendrick.core;

import jKendrick.simulation.Scenario;

public class Model {
	private Scenario s;
	private double step;
	private double end;
	private int nbCycles;
	
	public Model(Scenario s, double step, double end) {
		this.s=s;
		this.step=step;
		this.end=end;
		nbCycles=1;
	}
	
	public Model(Scenario s, double step, double end, int nbCycles) {
		this.s=s;
		this.step=step;
		this.end=end;
		this.nbCycles=nbCycles;
	}
	
	public Scenario getScenario() {
		return s;
	}
	
	public double getStep() {
		return step;
	}
	
	public double getEnd() {
		return end;
	}
	
	public int getNbCycles() {
		return nbCycles;
	}
}
