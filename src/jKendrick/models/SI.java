package jKendrick.models;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public class SI extends Model {
	public SI() {
		this("S I", "beta");
	}
	
	public SI(String cNames, String pNames) {
		super(cNames, pNames);
		assert getCompartmentName(0).contentEquals("beta");
	}
	
	public double beta() {
		return parameters[0]; // faster than get("beta")
	}

	@Override
	public void computeDerivatives(double t, double[] comp, double[] dot)
					throws MaxCountExceededException, DimensionMismatchException {
		dot[0] = -beta() * comp[0] * comp[1];
		dot[1] = beta() * comp[0] * comp[1]; 
		
		assert theSumOfDerivativesIsNull(dot);
	}

}
