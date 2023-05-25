package jKendrick.tools;

import java.util.Random;

public class PoissonGenerator {
	
	    private Random random;

	    public PoissonGenerator() {
	        random = new Random();
	    }

	    public int poissonSample(double mean) {
	        double expMean = Math.exp(-mean);
	        double p = 1.0;
	        int factor = 0;

	        while (p >= expMean) {
	            double u = random.nextDouble();
	            p *= u;
	            factor++;
	        }

	        return factor - 1;
	    }
	}


