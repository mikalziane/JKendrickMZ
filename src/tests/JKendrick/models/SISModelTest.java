package tests.JKendrick.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.models.SISModel;
import jKendrick.models.old.OldSIRModel;

class SISModelTest {
	
	double s0 = 0.999999; // initial proportion of Ss
	double i0 = 0.000001; // initial proportion of Is
	double[] arguments ={ s0, i0};
	double beta = 1.4247; // transmission rate
	double gamma = 0.14286; // recovery rate

	@Test
	void testDimension() {
		
		SISModel ode = new SISModel(beta, gamma);
		assertEquals(2, ode.getDimension());;
	}
	
	
	@Test
	void testParametersOfModels() {
		
		SISModel ode = new SISModel(beta, gamma);
		double[] sisDot = new double[arguments.length];
		ode.computeDerivatives(0, arguments, sisDot);
		
		assertEquals((-beta * i0 * s0) + (gamma * i0), sisDot[0]);
		assertEquals((-gamma * i0) + (beta * i0 * s0), sisDot[1]);
	}

}
