package jKendrick.concerns;

import jKendrick.IRate;
import jKendrick.scenario.Scenario;


public class Rate implements IRate {
	private String value;
	public Rate(String value) {
		this.value=value;
	}
	
	@Override
	public double getRate(Scenario s) {
		return s.getParam(value);
	}

	@Override
	public double eval(double t, double[] model) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
}
