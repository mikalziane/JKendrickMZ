package jKendrick.tools;

public class RouletteWheel {
	private double[] rates;
	private double[] sums;
	private final int nbRates;
	private final double epsilon;
	
	public RouletteWheel(double[] rates, double epsilon) {
		this.epsilon = epsilon;
		this.nbRates = rates.length;
		assert nbRates >0;
		this.rates = rates.clone();
		this.sums = new double[nbRates+1];
		sums[0] = 0.;
		for (int i =1; i<nbRates+1; ++i)
			sums[i] = sums[i-1] + rates[i-1];
	}
	public double getRate(int i) {
		return rates[i];
	}
	
	// returns sum of all rates[k] for k <i
	public double sum(int i) {
		assert i >=0 && i < nbRates+1;
		return sums[i];
	}
	public boolean equals (double x1, double x2) {
		return Math.abs(x1 - x2) < epsilon;
	}
	public boolean lesser(double x1, double x2) {
		return x1 < x2 || (x1 - epsilon) <x2;
	}
	
	public int getEvent(double rand) {
		// returns the first i such that sum(i) >= rand
		// where sum(i) is the sum of rates[k] for all k < i
		// 1 <= i && i <= taux.length
		// as soon as sum(i) >= rand, i-1 is returned
		assert rand >=0.0 && rand <= 1.0;
		int i;
		for (i = 1; i < rates.length && lesser(sum(i), rand) ; ++i)
			;
		return i-1;
	}
	
	public int getEvent() {
		return getEvent(Math.random());
	}
}
