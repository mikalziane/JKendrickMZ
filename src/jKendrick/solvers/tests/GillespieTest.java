package jKendrick.solvers.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jKendrick.events.Infection;
import jKendrick.events.Recovery;
import jKendrick.solvers.Gillespie;
import jKendrick.Visualization;
import jKendrick.events.IEvent;
class GillespieTest {

	@Test
	void test() {
		//test de gillespie sur SIR avec 10 cycles de 20 étapes
		int nbCycle=10;
		int nbStep=20;
		Map<String, Integer> nbIndiv=new HashMap<>();
		nbIndiv.put("S", 99);
		nbIndiv.put("I", 1);
		nbIndiv.put("R", 0);
		IEvent Infect=new Infection(0.6);
		IEvent Recov=new Recovery(0.2);
		IEvent[] events= {Infect,Recov}; 
		Gillespie g=new Gillespie(nbCycle, nbStep, nbIndiv, events);
		
		//on vérifie que la fonction getInitialPopulation retourne bien la population initiale
		double[] initialPop=g.getInitialPopulation();
		assertEquals(0.,initialPop[0]);
		assertEquals(99.,initialPop[1]);
		
		//on verifie que le tableau de résultat est initialisé avec la population initiale, et que le reste est initialisé à 0
		double[][][] initialTab=g.initResult();
		assertEquals(99.,initialTab[0][0][1]);
		assertEquals(99.,initialTab[9][0][1]);
		assertEquals(1.,initialTab[0][0][2]);
		assertEquals(1.,initialTab[9][0][2]);
		assertEquals(0.,initialTab[0][0][0]);
		assertEquals(0.,initialTab[9][0][0]);
		assertEquals(0.,initialTab[0][1][1]);
		
		
		//on verifie que les taux retournés par getRates sont corrects
		double[] initialRates=g.getRates(initialTab[0][0]);
		assertEquals(59.4,initialRates[0]);
		assertEquals(0.2,initialRates[1]);
		
		double[][][] result=g.solve();
		double[][] average=g.getAverage();
		
		//affichage des valeurs de résultat après les 10 cycles
		for(int a=0;a<result.length;++a) {
			System.out.println("Cycle "+a);
			for(int b=0;b<result[0].length;++b) {
				for(int c=0;c<result[0][0].length;++c) {
					System.out.print(result[a][b][c]+" - ");
				}
				System.out.println(" ");
			}
		}
		
		//affichage des valeurs moyennes
		System.out.println("Moyenne:");
		for(int i=0;i<average.length;++i) {
			for(int j=0;j<average[0].length;++j) {
				System.out.print(average[i][j]+" - ");
			}
			System.out.println(" ");
		}
		
		//vérifier qu'un nombre de cycles ou d'étapes inférieurs à 1 déclanchent une erreur
		assertThrows(AssertionError.class,
	            ()->{Gillespie g0Cycle=new Gillespie(0, nbStep, nbIndiv, events);} );
		assertThrows(AssertionError.class,
	            ()->{Gillespie g0Step=new Gillespie(nbCycle, 0, nbIndiv, events);} );
		
		Map<String, Integer> noIndiv=new HashMap<>();
		assertThrows(AssertionError.class,
	            ()->{Gillespie g0Indiv=new Gillespie(nbCycle, nbStep, noIndiv, events);} );
		IEvent[] noEvents=new IEvent[0];
		assertThrows(AssertionError.class,
	            ()->{Gillespie g0Event=new Gillespie(nbCycle, nbStep, nbIndiv, noEvents);} );
		
		double avStep=g.getAverageStep();
		System.out.println(avStep);
		
		double[][] values=g.getValues();
		double last=avStep*nbStep;
		String[] labels=nbIndiv.keySet().toArray((new String[nbIndiv.size()]));
		Visualization v=new Visualization();
		try {
		v.xchartExample(values, avStep, last, labels, "Test SIR", "population", "temps");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
