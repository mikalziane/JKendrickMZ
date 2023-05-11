package jKendrick.events;

public class Recovery extends Event {
	
	public Recovery(double rate) {
		super(rate);
	}
	@Override
	public double getRate(String[] compartments, double[] population) {
		assert population.length>=compartments.length;
		int indexI=indexOf(compartments, "I");
		assert indexI>=0;
		double r=super.getRate()*population[indexI];
		return r;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		assert population.length>=compartments.length;
		int indexI=indexOf(compartments, "I");
		int indexR=indexOf(compartments, "R");
		assert indexI>=0 && indexR>=0;
		double[] r=population.clone();
		if(r[indexI]>0) {
			r[indexI]--;
			r[indexR]++;
		}
		return r;
	}
	
	
	
}
