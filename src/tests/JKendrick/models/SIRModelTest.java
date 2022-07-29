package tests.JKendrick.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.models.SIRModel;

class SIRModelTest {

	@Test
	void test() {
		SIRModel ode = new SIRModel(1.4247, 0.14286);
		assertEquals(3, ode.getDimension());
	}

}
