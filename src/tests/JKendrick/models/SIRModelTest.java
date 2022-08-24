package tests.JKendrick.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.models.old.OldSIRModel;

class SIRModelTest {
	// https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.1/index.html
	// https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.1/Parameters_2_1

	@Test
	void test() {
		double s0 = 0.999999; // initial proportion of Ss
		double i0 = 0.000001; // initial proportion of Is
		double r0 = 0.0; // initial proportion of Rs
		double[] arguments ={ s0, i0, r0};
		double beta = 1.4247; // transmission rate
		double gamma = 0.14286; // recovery rate
		
		OldSIRModel ode = new OldSIRModel(beta, gamma);

		assertEquals(3, ode.getDimension());
		
		double[] sirDot = new double[arguments.length];
		ode.computeDerivatives(0, arguments, sirDot);
		
		assertEquals(- beta *s0 * i0, sirDot[0]);
		assertEquals(beta *s0 * i0 - gamma *i0, sirDot[1]);
		assertEquals(gamma * i0, sirDot[2]);
	}
}
