package jKendrick.events.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.events.EndOfImmunity;


class EndOfImmunityTest {

	@Test
	void test() {
		String[] compartments= {"S","I","R"};
		String[] wrongCompartments= {"A","S","Z"};
		String[] emptyCompartments= {};
		double[] population= {2.,3.,4.};
		double[] smallPopulation= {1.,2.};
		double[] noRpopulation= {2.,3.,0.};
		double[] noIpopulation= {2.,0.,4.};
		EndOfImmunity eoi=new EndOfImmunity(0.5);
		
		//test de la methode getRate
		assertEquals(2.0, eoi.getRate(compartments,population));
		
		assertThrows(AssertionError.class,
	            ()->{eoi.getRate(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{eoi.getRate(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{eoi.getRate(emptyCompartments,population);} );
		
		//test de la methode action
		double[] updatedPopulation=eoi.action(compartments, population);
		assertEquals(3.,updatedPopulation[0]);
		assertEquals(3.,updatedPopulation[1]);
		assertEquals(3.,updatedPopulation[2]);
		assertThrows(AssertionError.class,
	            ()->{eoi.action(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{eoi.action(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{eoi.action(emptyCompartments,population);} );
		//verifier que la population de change pas s'il n'y a aucun R 
		updatedPopulation=eoi.action(compartments, noRpopulation);
		assertEquals(2.,updatedPopulation[0]);
		assertEquals(3.,updatedPopulation[1]);
		assertEquals(0.,updatedPopulation[2]);
		
	}

}
