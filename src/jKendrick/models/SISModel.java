package jKendrick.models;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;


// https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.5/index.html
public class SISModel implements FirstOrderDifferentialEquations {
	
	private double beta;
	private double gamma;

	public SISModel(double beta, double gamma){
		this.beta = beta;
		this.gamma = gamma;
		}

	@Override
	public void computeDerivatives(double t, double[] sis, double[] sisDot)
			throws MaxCountExceededException, DimensionMismatchException {
	
		sisDot[0] = (-beta * sis[1] * sis[0]) + (gamma * sis[1]);
		sisDot[1] = (-gamma * sis[1]) + (beta * sis[1] * sis[0]);
		
	}

@Override
	public int getDimension() {
		return 2;
	}

}
