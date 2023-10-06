package jKendrick.tests.tools;

import org.junit.jupiter.api.Test;

import jKendrick.tools.GeneralizedRW;

class GeneralizedRWTest {
	@Test
	void test() {
		final double h = 2.0;
		double[] rates1x2 = RouletteWheelTest.rates1.clone();
		for (int i =0; i < rates1x2.length; ++i)
			rates1x2[i] *= h;
		RouletteWheelTest.testWith(h, new GeneralizedRW(rates1x2, RouletteWheelTest.epsilon),
				rates1x2, RouletteWheelTest.exp_sums1, RouletteWheelTest.rands1,
				RouletteWheelTest.exp_events1, RouletteWheelTest.epsilon);		
	}
}
