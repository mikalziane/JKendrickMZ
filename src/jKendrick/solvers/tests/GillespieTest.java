package jKendrick.solvers.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jKendrick.events.Infection;
import jKendrick.events.Recovery;
import jKendrick.solvers.Gillespie;
import jKendrick.events.IEvent;
class GillespieTest {

	@Test
	void test() {
		int nbCycle=10;
		int nbStep=15;
		Map<String, Integer> nbIndiv=new HashMap<>();
		nbIndiv.put("S", 99);
		nbIndiv.put("I", 1);
		nbIndiv.put("R", 0);
		IEvent Infect=new Infection(0.6);
		IEvent Recov=new Recovery(0.2);
		IEvent[] events= {Infect,Recov}; 
		Gillespie g=new Gillespie(nbCycle, nbStep, nbIndiv, events);
		
		double[] initialPop=g.getInitialPopulation();
		assertEquals(0.,initialPop[0]);
		assertEquals(99.,initialPop[1]);
		
		double[][][] initialTab=g.initResult();
		assertEquals(99.,initialTab[0][0][1]);
		assertEquals(99.,initialTab[9][0][1]);
		assertEquals(1.,initialTab[0][0][2]);
		assertEquals(1.,initialTab[9][0][2]);
		assertEquals(0.,initialTab[0][0][0]);
		assertEquals(0.,initialTab[9][0][0]);
		assertEquals(0.,initialTab[0][1][1]);
		
		
		
		double[] initialRates=g.getRates(initialTab[0][0]);
		assertEquals(59.4,initialRates[0]);
		assertEquals(0.2,initialRates[1]);
		
		double[][][] result=g.solve();
		double[][] average=g.getAverage();
		
		
		
		
	}

}
