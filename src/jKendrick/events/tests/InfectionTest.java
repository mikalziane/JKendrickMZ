package jKendrick.events.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.events.Infection;
import jKendrick.solvers.Gillespie;

class InfectionTest {

	@Test
	void test() {
		String[] compartments= {"S","I","R"};
		String[] wrongCompartments= {"A","S","Z"};
		String[] emptyCompartments= {};
		double[] population= {2.,3.,4.};
		double[] smallPopulation= {1.,2.};
		double[] noSpopulation= {0.,3.,4.};
		double[] noIpopulation= {2.,0.,4.};
		Infection inf=new Infection(0.5);
		
		//test de la methode getRate
		assertEquals(3.0, inf.getRate(compartments,population));
		
		assertThrows(AssertionError.class,
	            ()->{inf.getRate(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{inf.getRate(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{inf.getRate(emptyCompartments,population);} );
		
		//test de la methode action
		double[] updatedPopulation=inf.action(compartments, population);
		assertEquals(1.,updatedPopulation[0]);
		assertEquals(4.,updatedPopulation[1]);
		assertEquals(4.,updatedPopulation[2]);
		assertThrows(AssertionError.class,
	            ()->{inf.action(wrongCompartments,population);} );
		assertThrows(AssertionError.class,
	            ()->{inf.action(compartments,smallPopulation);} );
		assertThrows(AssertionError.class,
	            ()->{inf.action(emptyCompartments,population);} );
		//verifier que la population de change pas s'il n'y a aucun S ou aucun I
		updatedPopulation=inf.action(compartments, noSpopulation);
		assertEquals(0.,updatedPopulation[0]);
		assertEquals(3.,updatedPopulation[1]);
		assertEquals(4.,updatedPopulation[2]);
		updatedPopulation=inf.action(compartments, noIpopulation);
		assertEquals(2.,updatedPopulation[0]);
		assertEquals(0.,updatedPopulation[1]);
		assertEquals(4.,updatedPopulation[2]);
		
		
		
		
		
	}

}
