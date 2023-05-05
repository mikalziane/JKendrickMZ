package jKendrick.events;

public class Infection extends Event{
	

	public Infection(double rate) {
		super(rate);
	}

	@Override
	public double getRate(String[] compartments, double[] population) {
		double r=0;
		int indexS=indexOf(compartments, "S");
		int indexI=indexOf(compartments, "I");
		assert indexS>=0 && indexI>=0;
		double nbS=population[indexS];
		double nbI=population[indexI];
		if(nbS>nbI) {
			r=super.getRate()*nbI;
		}
		else {
			r=super.getRate()*nbS;
		}
		return r;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		int indexS=indexOf(compartments, "S");
		int indexI=indexOf(compartments, "I");
		assert indexS>=0 && indexI>=0;
		double[] r=population.clone();
		if(r[indexS]>0 && r[indexI]>0) {
			r[indexS]--;
			r[indexI]++;
		}
		return r;
	}
	

	
}
