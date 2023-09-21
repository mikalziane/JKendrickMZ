package jKendrick.models.old;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public class SIR extends SI  {
	public SIR(){
		super("S I R", "beta gamma");
	}
	
	public double gamma() {
		return parameters[1];
	}
	
	@Override
	public void computeDerivatives(double t, double[] sir, double[] sirDot)
					throws MaxCountExceededException, DimensionMismatchException {
		sirDot[0] = (-beta() * sir[0] * sir[1]);
		sirDot[1] = (beta() * sir[0] * sir[0]) - (gamma() * sir[1]);
		sirDot[2] = gamma() * sir[1];
		
		assert theSumOfDerivativesIsNull(sirDot);
	}


}
