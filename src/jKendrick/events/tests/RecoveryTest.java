package jKendrick.events.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.events.Recovery;

class RecoveryTest {

	@Test
	void test() {
		String[] compartments= {"S","I","R"};
		String[] wrongCompartments= {"A","S","Z"};
		String[] emptyCompartments= {};
		double[] population= {2.,3.,4.};
		double[] smallPopulation= {1.,2.};
		double[] noIpopulation= {2.,0.,4.};
		Recovery rec=new Recovery(0.5);
		
		//test de la methode getRate
		assertEquals(1.5, rec.getRate(compartments,population));
		
		assertThrows(AssertionError.class,
	            ()->{rec.getRate(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{rec.getRate(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{rec.getRate(emptyCompartments,population);} );
		
		//test de la methode action
		double[] updatedPopulation=rec.action(compartments, population);
		assertEquals(2.,updatedPopulation[0]);
		assertEquals(2.,updatedPopulation[1]);
		assertEquals(5.,updatedPopulation[2]);
		assertThrows(AssertionError.class,
	            ()->{rec.action(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{rec.action(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{rec.action(emptyCompartments,population);} );
		//verifier que la population de change pas s'il n'y a aucun I
		updatedPopulation=rec.action(compartments, noIpopulation);
		assertEquals(2.,updatedPopulation[0]);
		assertEquals(0.,updatedPopulation[1]);
		assertEquals(4.,updatedPopulation[2]);
	}

}
