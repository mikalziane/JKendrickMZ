package tests.JKendrick.models;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jKendrick.models.SEIR;

public class SEIRModelTest {

	double s0 = 0.1; // initial proportion of Ss
	double e0 = 0.0001; // initial proportion of Es
	double i0 = 0.0001; // initial proportion of Is
	double r0 = 0.898; // initial proportion of Rs
	double[] arguments ={ s0, e0, i0, r0};
	double beta = 1.4247; // transmission rate
	double gamma = 0.14286; // recovery rate
	double mu = 0.0000391; // Birth_and_death_rate
	double sigma = 0.07143; // Exposed_to_Infectious_rate
	

	@Test
	void testDimension() {
		
		SEIR ode = new SEIR(beta, gamma, mu, sigma);
		assertEquals(4, ode.getDimension());;
	}
	
	
	@Test
	void testParametersOfModels() {
		
		SEIR ode = new SEIR(beta, gamma, mu, sigma);
		double[] seirDot = new double[arguments.length];
		ode.computeDerivatives(0, arguments, seirDot);
		
		assertEquals(mu - ((beta * i0 + mu) * s0), seirDot[0]);
		assertEquals((beta * i0 * s0) - ((mu + sigma) * e0), seirDot[1]);
		assertEquals((sigma * e0) - ((mu + gamma) * i0), seirDot[2]);
		assertEquals((gamma * i0) - (mu * r0), seirDot[3]);
	}

}
