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
		this.sums = new double[rates.length];
		sums[0] = rates[0];
		for (int i =1; i<rates.length; ++i)
			sums[i] = sums[i-1] + rates[i];
	}
	public double getRate(int i) {
		return rates[i];
	}
	public double getSum(int i) {
		assert i >=0 && i < nbRates;
		return sums[i];
	}
	public boolean equals (double x1, double x2) {
		return Math.abs(x1 - x2) < epsilon;
	}
	public boolean lesser(double x1, double x2) {
		return x1 < x2 || equals(x1, x2);
	}
	public int getEvent(double rand) {
		// returns the first i such that sum >= rand
		// where sum is the sum of rates[k] for all k < i
		// 1 <= i && i <= taux.length
		// as soon as sum >= rand, i-1 is returned
		assert rand >=0.0 && rand <= 1.0;
		System.out.println("RAND = "+ rand);
		int i;
		double sum = rates[0];
		for (i = 1; sum < rand && i < rates.length; ++i) {
			System.out.println(i+" sum: "+sum+ " rand: "+ rand);
			sum += rates[i];
		}
		System.out.println("finally " +i+" sum: "+sum+ " rand: "+ rand+"\n\n");
		return i-1;
	}
	
	public int getEvent() {
		return getEvent(Math.random());
	}
}
