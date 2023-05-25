package jKendrick.events;

public class Infection extends Event{
	

	public Infection(double rate) {
		super(rate);
	}

	@Override
	public double getRate(String[] compartments, double[] population) {
		double r=0;
		assert population.length>=compartments.length;
		int totalPop=totalPop(population);
		int indexS=indexOf(compartments, "S");
		int indexI=indexOf(compartments, "I");
		assert indexS>=0 && indexI>=0;
		double nbS=population[indexS];
		double nbI=population[indexI];
		r=(super.getRate()*nbI*nbS)/totalPop;
		return r;
	}
	
	public int totalPop(double[] population) {
		int total=0;
		for(int i=0;i<population.length;++i) {
			total+=population[i];
		}
		return total;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		assert population.length>=compartments.length;
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
