package tests.JKendrick.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.models.SISModel;

class SISModelTest {
	
	double s0 = 0.999999; // initial proportion of Ss
	double i0 = 0.000001; // initial proportion of Is
	double[] compartments ={ s0, i0};
	double beta = 1.4247; // transmission rate
	double gamma = 0.14286; // recovery rate
	double precision = 0.000000001; // assertEquals on doubles

	@Test
	void testDimension() {
		
		SISModel model = new SISModel(beta, gamma);
		assertEquals(2, model.getDimension());;
	}
	
	
	@Test
	void testParametersOfModels() {
		
		
		SISModel model = new SISModel(beta, gamma);
		double[] sisDot = new double[compartments.length];
		model.computeDerivatives(0, compartments, sisDot);
		
		double infectionRate0 = beta * i0 * s0;
		double recoveryRate0 = gamma * i0;
		assertEquals(infectionRate0, model.infectionRate(0, compartments), precision);
		assertEquals(recoveryRate0, model.recoveryRate(0, compartments), precision);
		
		assertEquals(-infectionRate0 + recoveryRate0, sisDot[0], precision);
		assertEquals(-recoveryRate0 + infectionRate0, sisDot[1], precision);
	}

}
