package jKendrick.events;

public class Infection implements IEvent{
	private double rate;
	public Infection(double rate) {
		this.rate=rate;
	}

	@Override
	public double getRate(String[] compartments, double[] population) {
		double r=0;
		int[] indexSI=indexOfSI(compartments);
		int indexS=indexSI[0];
		int indexI=indexSI[1];
		assert indexS>=0 && indexI>=0;
		r=rate*population[indexS]*population[indexI];
		return r;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		int[] indexSI=indexOfSI(compartments);
		int indexS=indexSI[0];
		int indexI=indexSI[1];
		assert indexS>=0 && indexI>=0;
		double[] r=population.clone();
		if(r[indexS]>0 && r[indexI]>0) {
			r[indexS]--;
			r[indexI]++;
		}
		return r;
	}
	
	public int[] indexOfSI(String[] compartments) {
		int[] indexSI= {-1,-1};
		for(int i=0;i<compartments.length;++i) {
			if(compartments[i].equals("S")) {
				indexSI[0]=i;
			}
			else if(compartments[i].equals("I")) {
				indexSI[1]=i;
			}
		}
		return indexSI;
	}
	
}
