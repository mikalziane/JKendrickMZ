package tests.JKendrick.concerns;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.concerns.Concern;
import jKendrick.concerns.IRates;

import jKendrick.concerns.Rate;
class ConcernTest {

	@Test
	void test() {
		Concern ABC=new Concern("A B C", "x y z");
		
		assertEquals(0, ABC.indexOfCompartment("A"));
		assertEquals(2, ABC.indexOfCompartment("C"));
		
		assertEquals(0, ABC.getParameter("x"));
		
		ABC.setParameter("y", 1.5);
		
		assertEquals(1.5, ABC.getParameter("y"));
		IRates z=new Rate("z");
		IRates x=new Rate("x");
		IRates y=new Rate("y");
		
		ABC.setTransitionRate("B", "C", z);
		ABC.setTransitionRate("A", "C", x);
		ABC.setTransitionRate("C", "A", y);
		
		System.out.println(ABC);
		
		assertThrows(AssertionError.class,
	            ()->{new Concern("", "x y z");} );
		
		Concern noParam=new Concern("A B C", "");
		noParam.setParameter("i", 2.3);
		assertEquals(2.3, noParam.getParameter("i"));
		
		
	}

}
