package jKendrick.models.old;

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
	
	// beta IS
	public double infectionRate(double t, double[] sis) {
		return beta *sis[1] * sis[0];
	}
	
	// gamma I
	public double recoveryRate(double t, double[] sis) {
		return gamma * sis[1];
	}
	
	@Override
	public int getDimension() {
		return 2;
	}

	@Override
	public void computeDerivatives(double t, double[] sis, double[] sisDot)
			throws MaxCountExceededException, DimensionMismatchException {
		sisDot[0] = -infectionRate(t, sis) + recoveryRate(t, sis);
		sisDot[1] =  infectionRate(t, sis) - recoveryRate(t, sis);
	}
}
