package jKendrick.solvers;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import jKendrick.models.SISModel;

class GillespieDMNaiveTest {

	@Test
	void test() {
		// on veut tester SSA sur un modele SIS sans demographie
		// 2 evenements : infection et guerison
		// taux de l'infection : beta SI
		// taux de la guerison : gamma I
		
		SSANaive ssa = new SSANaive(2);
		assertEquals(2, ssa.getNbEvents());
		assertEquals(0, ssa.getNbRates());
		
		SISModel model = new SISModel(1.4247, 0.14286);
		
		ssa.addFrate(model::infectionRate);
		ssa.addFrate(model::recoveryRate);
		assertEquals(2, ssa.getNbRates());
	}

}
