package jKendrick.rates;

import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;

public class SumRate implements IRates{
	private IRates r1;
	private IRates r2;
	
	public SumRate(IRates r1, IRates r2) {
		this.r1=r1;
		this.r2=r2;
	}

	

	@Override
	public double getRate(Scenario s) {
		return r1.getRate(s)+r2.getRate(s);
	}
}
