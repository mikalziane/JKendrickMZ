package tests.jKendrick.rates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jKendrick.rates.DivRate;
import jKendrick.rates.Rate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;

class DivRateTest {

	@Test
	void test() {
		Concern ABC=new Concern("A B C", "X Y Z");
		IRates X=new Rate("X");
		IRates Y=new Rate("Y");
		IRates Z=new Rate("Z");
		IRates XY=new DivRate(X, Y);
		IRates XZ=new DivRate(X, Z);
		ABC.setTransitionRate("A", "B", XY);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(ABC);
		
		Scenario ABCScenario=new Scenario(concerns);
		ABCScenario.setParameter("A", 1.);
		ABCScenario.setParameter("B", 3.);
		ABCScenario.setParameter("C", 4.);
		ABCScenario.setParameter("X", 5.);
		ABCScenario.setParameter("Y", 2.);
		
		assertEquals(2.5,XY.getRate(ABCScenario));
		assertThrows(AssertionError.class,
	            ()->{XZ.getRate(ABCScenario);} );
		
	}

}
