package tests.jKendrick.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jKendrick.rates.Rate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;

class ScenarioTest {

	@Test
	void test() {
		Concern ABC=new Concern("A B C", "x y z");
		IRates z=new Rate("z");
		IRates x=new Rate("x");
		IRates y=new Rate("y");
		
		ABC.setTransitionRate("B", "C", z);
		ABC.setTransitionRate("A", "C", x);
		ABC.setTransitionRate("C", "A", y);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(ABC);
		
		Scenario ABCScenario=new Scenario(concerns);
		assertEquals("A", ABCScenario.getCompartments().get(0));
		assertEquals("B", ABCScenario.getCompartment(1));
		assertEquals(2,ABCScenario.indexOf("C"));
		assertEquals(3,ABCScenario.getNbCompartments());
		
		ABCScenario.setParameter("x", 2.);
		ABCScenario.setParameter("A", 3.);
		ABCScenario.setParameter("B", 6.);
		ABCScenario.setParameter("C", 0.);
		assertEquals(0.,ABCScenario.getParam("y"));
		assertEquals(2.,ABCScenario.getParam("x"));
		ABCScenario.transition("A", "B");
		assertEquals(2.,ABCScenario.getParam("A"));
		assertEquals(7.,ABCScenario.getParam("B"));
		assertEquals(9.,ABCScenario.getN());
		assertEquals(7.,ABCScenario.getPopulation()[1]);
		assertEquals(3,ABCScenario.getDimension());
		
 	}

}
