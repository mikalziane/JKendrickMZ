package jKendrick.events;

public class EndOfImmunity extends Event{
	

	public EndOfImmunity(double rate) {
		super(rate);
	}

	@Override
	public double getRate(String[] compartments, double[] population) {
		double r=0;
		assert population.length>=compartments.length;
		int indexR=indexOf(compartments, "R");
		assert indexR>=0;
		r=super.getRate()*population[indexR];
		return r;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		assert population.length>=compartments.length;
		int indexS=indexOf(compartments, "S");
		int indexR=indexOf(compartments, "R");
		assert indexS>=0 && indexR>=0;
		double[] r=population.clone();
		if(r[indexR]>0) {
			r[indexR]--;
			r[indexS]++;
		}
		return r;
	}
	
}
