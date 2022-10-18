package jKendrick.tools;

public class GeneralizedRW extends RouletteWheel {

	public GeneralizedRW(double[] rates, double epsilon) {
		super(normalize(rates), epsilon);
	}

	private static double[] normalize(double[] rates) {
		double[] nrates = new double[rates.length];
		double sum = 0.;
		for (double d : rates)
			sum += d;
		assert sum != 0.;
		for (int i=0; i != rates.length; ++i)
			nrates[i] = rates[i] / sum;
		return nrates;
	}

}
