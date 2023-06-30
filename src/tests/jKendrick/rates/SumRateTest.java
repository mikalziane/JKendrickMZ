package tests.jKendrick.rates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jKendrick.rates.Rate;
import jKendrick.rates.SumRate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;

class SumRateTest {

	@Test
	void test() {
		Concern ABC=new Concern("A B C", "X Y");
		IRates X=new Rate("X");
		IRates Y=new Rate("Y");
		IRates XY=new SumRate(X, Y);
		
		ABC.setTransitionRate("A", "B", XY);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(ABC);
		
		Scenario ABCScenario=new Scenario(concerns);
		ABCScenario.setParameter("A", 1.);
		ABCScenario.setParameter("B", 3.);
		ABCScenario.setParameter("C", 4.);
		ABCScenario.setParameter("X", 2.);
		ABCScenario.setParameter("Y", 5.);
		
		assertEquals(7.,XY.getRate(ABCScenario));
	}

}
