package tests.jKendrick.solvers;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import jKendrick.models.old.SISModel;
import jKendrick.solvers.SSANaive;

class GillespieSSANaiveTest {
	double precision = 0.000000001;

	@Test
	void test() {
		// on veut tester SSA sur un modele SIS sans demographie
		// 2 evenements : infection et guerison
		// taux de l'infection : beta SI
		// taux de la guerison : gamma I
		
		SSANaive ssa = new SSANaive(2);
		assertEquals(2, ssa.getNbEvents());
		assertEquals(0, ssa.getNbRates());

		double beta = 1.4247; // transmission rate
		double gamma = 0.14286; // recovery rate
		SISModel model = new SISModel(beta, gamma);

		ssa.addFrate(model::recoveryRate);
		ssa.addFrate(model::infectionRate);
		assertEquals(2, ssa.getNbRates());
		
		double s0 = 0.999999; // initial proportion of Ss
		double i0 = 0.000001; // initial proportion of Is
		double[] compartments ={ s0, i0};
		double infectionRate0 = beta * i0 * s0;
		double recoveryRate0 = gamma * i0;
		
		double sum = ssa.getRateSum(0 ,compartments);
		System.out.println("infectionRate0 = "+infectionRate0);
		System.out.println("recoveryRate0 = "+recoveryRate0);
		System.out.println("sum = "+ sum);
		assertEquals(infectionRate0 +  recoveryRate0, sum, precision);
		for (int i = 0; i <10; ++i) {
				int ie = ssa.getEvent(0, compartments);
				System.out.println("event "+ ie);
		}
		
	}

}
