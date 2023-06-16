package jKendrick.concerns;

import jKendrick.scenario.Scenario;

public class DivRate implements IRates{
	private IRates r1;
	private IRates r2;
	
	public DivRate(IRates r1, IRates r2) {
		this.r1=r1;
		this.r2=r2;
	}

	

	@Override
	public double getRate(Scenario s) {
		assert r2.getRate(s)!=0.;
		return r1.getRate(s)/r2.getRate(s);
	}

}
