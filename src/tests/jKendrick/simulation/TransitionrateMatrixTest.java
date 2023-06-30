package tests.jKendrick.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jKendrick.rates.Rate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;
import jKendrick.simulation.TransitionRateMatrix;

class TransitionrateMatrixTest {

	@Test
	void test() {
		IRates z=new Rate("z");
		IRates x=new Rate("x");
		IRates y=new Rate("y");
		
		ArrayList<String> compartments=new ArrayList<String>();
		compartments.add("a");
		compartments.add("b");
		compartments.add("c");
		TransitionRateMatrix tr=new TransitionRateMatrix(compartments);
		tr.setRate("a", "b", y);
		tr.setRate("a", "c", x);
		assertThrows(AssertionError.class,
	            ()->{tr.setRate("a", "d", y);} );
		tr.addCompartment("d");
		tr.setRate("a", "d", y);
		String[][] events=tr.getPossibleEvents();
		assertEquals(3,events.length);
		assertEquals(2,events[0].length);
		assertEquals("a",events[0][0]);
		assertTrue(events[0][1].equals("b")||events[0][1].equals("c")||events[0][1].equals("d"));
		
		
		Concern ABC=new Concern("A B C", "x y z");
		
		
		ABC.setTransitionRate("B", "C", z);
		ABC.setTransitionRate("A", "C", x);
		ABC.setTransitionRate("C", "A", y);
		ABC.setTransitionRate("B", "A", x);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(ABC);
		
		Scenario ABCScenario=new Scenario(concerns);
		ABCScenario.setParameter("x", 2.);
		ABCScenario.setParameter("y", 4.);
		ABCScenario.setParameter("z", 1.);
		ABCScenario.setParameter("A", 3.);
		ABCScenario.setParameter("B", 6.);
		ABCScenario.setParameter("C", 0.);
		
		assertThrows(AssertionError.class,
	            ()->{ABCScenario.getTransitions().getRate("E", "B", ABCScenario);} );
		assertEquals(-3.,ABCScenario.getTransitions().getRate("B", "B", ABCScenario));
		assertEquals(4,ABCScenario.getTransitions().getRate("C", "A", ABCScenario));
		assertEquals(0,ABCScenario.getTransitions().getRate("A", "B", ABCScenario));
		
		
		
	}

}
