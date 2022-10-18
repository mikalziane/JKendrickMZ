package jKendrick.tools;


public class RouletteWheel {
	private double[] rates;   // rates (proportions to enforce) of events
				// the total sum of rates must equals 1
	private double[] sums;    // cumulative sums of rates 
				// sum[i] = rates[0] + ... rates[i]
	private final double epsilon;  // precision for comparisons
	
	public RouletteWheel(double[] rates, double epsilon) {
		this.epsilon = epsilon;
		assert rates.length >0;
		this.rates = rates.clone();
		this.sums = new double[rates.length];
		sums[0] = rates[0];
		for (int i =1; i<rates.length; ++i)
			sums[i] = sums[i-1] + rates[i];
		assert equals(1., sums[rates.length -1]);
		// to be sure a provided rand is never bigger
		// than the last sum:
		sums[rates.length -1] += 2* epsilon; 
	}

	public double rate(int i) {
		assert i >=0 && i < rates.length;
		return rates[i];
	}
	
	// sum of all rates[k] for k <= i
	public double sum(int i) {
		assert i >=0 && i < rates.length;
		return sums[i];
	}
	
	public boolean equals (double x1, double x2) {
		return Math.abs(x1 - x2) < epsilon;
	}
	public boolean lesser(double x1, double x2) {
		return x1 < x2 || (x1 - epsilon) <x2  || (x1 + epsilon) <x2;
	}
	
	// returns the first i for which  sum(i) >= rand
	public int getEvent(double rand) {
		assert rand >=0.0 && rand <= 1.0;
		int i;
		for (i = 0; i < rates.length && lesser(sum(i), rand) ; ++i)
			;
		assert i < rates.length;
		return i;
	}
	
	public int getEvent() {
		return getEvent(Math.random());
	}
}
