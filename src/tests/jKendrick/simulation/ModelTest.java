package tests.jKendrick.simulation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jKendrick.rates.Rate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Scenario;
import jKendrick.simulation.Model;

class ModelTest {

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
		Model ABCModel=new Model(ABCScenario, 0.5, 45., 10);
		assertEquals(0.5,ABCModel.getStep());
		assertEquals(45,ABCModel.getEnd());
		assertEquals(10,ABCModel.getNbCycles());
	}

}
