package tests.JKendrick.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.tools.RouletteWheel;

class RouletteWheelTest {
	final static double epsilon = 0.0000001;
	static double[] rates1 = {.2, .8};
	static double[] exp_sums1  = {.2, 1.+2*epsilon};
	static double[] rands1 = {.0, .1, .1999, .2, .2001,
			.5, .7999, .8, .8001, 1.0};
	static int[] exp_events1 = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1};
	
	static double[] rates2 = {.5, .2, .3};
	static double[] exp_sums2 =  {.5, .7, 1.+2*epsilon};
	static double[] rands2 = {.0, .4999, .5, .6999, .7, .7001, 1.0};
	static int[] exp_events2 = {0, 0, 1, 1, 2, 2, 2};
	
	
	@Test
	void test() {
		testWith(rates1, exp_sums1, rands1, exp_events1, epsilon);
		testWith(rates2, exp_sums2, rands2, exp_events2, epsilon);		
	}
	
	// normalized rates
	public static void testWith(double[] rates, double[] exp_sums,
			double[] rands, int[] exp_events, double epsilon) {
		 testWith(1.0, new RouletteWheel(rates, epsilon), rates, exp_sums,
				rands, exp_events, epsilon);
	}
	
	// general rates
	public static void testWith(double h, RouletteWheel r, double[] rates, double[] exp_sums,
			double[] rands, int[] exp_events, double epsilon) {
		assert h != 0.;
		assert rands.length == exp_events.length;
		assert exp_sums.length == rates.length;
		int i,j;
		for(i =0; i != rates.length; ++i) 
			assertEquals(rates[i]/h, r.rate(i), epsilon);
		for(i =0; i != rates.length; ++i) 
			assertEquals(exp_sums[i], r.sum(i), epsilon);
		for(j =0; j < rands.length; ++j)
			assertEquals(exp_events[j], r.getEvent(rands[j]));
	}

}
