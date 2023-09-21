package tests.JKendrick.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jKendrick.tools.TensorSum;

class TensorSumTest {

	@Test
	void test() {
		double[][] a= {{1.,2.,3.},{4.,5.,6.},{7.,8.,9.}};
		double[][] b= {{1.,2.},{3.,4.}};
		double[][] iA= {{1.,0.,0.},{0.,1.,0.},{0.,0.,1.}};
		assertArrayEquals(iA,TensorSum.getIdentity(a));
		
		double[][][][] pB= {{{{1.,2.},{3.,4.}},{{0.,0.},{0.,0.}},{{0.,0.},{0.,0.}}},
				{{{0.,0.},{0.,0.}},{{1.,2.},{3.,4.}},{{0.,0.},{0.,0.}}},
				{{{0.,0.},{0.,0.}},{{0.,0.},{0.,0.}},{{1.,2.},{3.,4.}}}};
		assertArrayEquals(pB, TensorSum.tensorProduct(iA, b));
		
		double[][][][] sum= {{{{2.,2.},{3.,5.}},{{2.,0.},{0.,2.}},{{3.,0.},{0.,3.}}},
				{{{4.,0.},{0.,4.}},{{6.,2.},{3.,9.}},{{6.,0.},{0.,6.}}},
				{{{7.,0.},{0.,7.}},{{8.,0.},{0.,8.}},{{10.,2.},{3.,13.}}}};
		assertArrayEquals(sum, TensorSum.getTensorSum(a, b));
		
	}

}
