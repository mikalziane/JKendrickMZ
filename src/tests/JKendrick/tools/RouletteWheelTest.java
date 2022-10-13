package tests.JKendrick.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.tools.RouletteWheel;
import junit.framework.AssertionFailedError;

class RouletteWheelTest {
	@Test
	void test() {
		final double epsilon = 0.00001;
		double[] rates1 = { .2, .8};
		double[] sums1 = {.2, 1.};
		double[] rands1 = {.0, .1, .1999, .2, .200001,
				.5, .7999, .8, .8001, 1.0};

		int[] events1 = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1};
//		testWith(rates1, sums1, rands1, events1, epsilon);
		
		double[] rates2 = { .5, .2, .3};
		double[] sums2 = {.5, .7, 1.};
		double[] rands2 = {.0, .49999, .5, .6999, .7, .70001, 1.0};
		int[] expected2 = {0, 0, 1, 1, 2, 2, 2};
		testWith(rates2, sums2, rands2, expected2, epsilon);		
	}
	
	void testWith(double[] rates, double[] exp_sums,
			double[] rands, int[] exp_events, double epsilon) {
		assert rands.length == exp_events.length;
		assert exp_sums.length == rates.length;
		RouletteWheel r = new RouletteWheel(rates, epsilon);
		int i,j;
		for(i =0; i != rates.length; ++i) 
			assertEquals(rates[i], r.getRate(i), epsilon);
		for(i =0; i != rates.length; ++i) 
			assertEquals(exp_sums[i], r.getSum(i), epsilon);
		for(j =0; j < rands.length; ++j) 
			assertEquals(exp_events[j], r.getEvent(rands[j]));
	}

}
