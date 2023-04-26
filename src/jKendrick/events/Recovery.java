package jKendrick.events;

public class Recovery implements IEvent {
	private double rate;
	public Recovery(double rate) {
		this.rate=rate;
	}
	@Override
	public double getRate(String[] compartments, double[] population) {
		int indexI=indexOf(compartments, "I");
		assert indexI>=0;
		double r=rate*population[indexI];
		return r;
	}

	@Override
	public double[] action(String[] compartments, double[] population) {
		int indexI=indexOf(compartments, "I");
		int indexR=indexOf(compartments, "R");
		double[] r=population.clone();
		if(r[indexI]>0) {
			r[indexI]--;
			r[indexR]++;
		}
		return r;
	}
	
	public int indexOf(String[] compartments, String x) {
		int index=-1;
		for(int i=0;i<compartments.length;++i) {
			if(compartments[i].equals(x)) {
				index=i;
			}
		}
		return index;
		
	}
	
}
