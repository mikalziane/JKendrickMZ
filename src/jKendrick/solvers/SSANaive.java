package jKendrick.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;





public class SSANaive {
	private List<IRate> rates;
	private int nbEvents;
	private Random random;
	
	public SSANaive (int nbEvents) {
		if (nbEvents <= 0)
			throw new IllegalArgumentException("At leat one event is expected");
		this.nbEvents = nbEvents;
		this.rates = new ArrayList<>();
		this.random = new Random();
	}
	public int getNbRates() { return rates.size();	}
	public int getNbEvents() { return nbEvents; }
	public void addFrate(IRate fr) {
		if (getNbRates() == getNbEvents())
			throw new IllegalStateException(
				"There are already as many rates as there are events.");
		rates.add(fr);
	}
	public IRate get(int i) {
		if (i <0 || i >= getNbRates())
			throw new IllegalArgumentException("invalide rate number : "+i);
		return rates.get(i);
	}
	public double getRateSum(double t, double[] cardinalities) {
		double sum = 0.;
		for (IRate rate : rates)
			sum += rate.eval(t, cardinalities);
		return sum;
	}
	public double getDeltaT(double t, double[] cardinalities) {
		double rand1 = random.nextDouble();
		return - Math.log(rand1) /getRateSum(t, cardinalities);
	}
	 public double getP(double t, double[] cardinalities) {
	        double rand2 =  random.nextDouble();
	        return rand2 * getRateSum(t, cardinalities);
	        
	  }
	 public int getEvent(double t, double[] cardinalities) {
		 int sum = 0;
		 int p;
		 double pRand = getP(t, cardinalities);
	     for (p=0;  sum < pRand && p<= nbEvents-1; ++p) 
	    	 sum += rates.get(p).eval(t, cardinalities);
	     return p-1;
	 }
}
