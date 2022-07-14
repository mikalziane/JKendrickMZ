package jKendrick.odes;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

public class ODESolver implements FirstOrderDifferentialEquations {
	private double[] parameters;
	private FirstOrderIntegrator integ;
	
	public ODESolver(double[] parameters) {
		this.parameters = new double[parameters.length];
		for (int i = 0; i < parameters.length; ++i)
			this.parameters[i] = parameters[i];
		integ = new ClassicalRungeKuttaIntegrator(0.1);
	}

	@Override
	public void computeDerivatives(double t, double[] sir, double[] sirDot)
			throws MaxCountExceededException, DimensionMismatchException {
		
	}

	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}
}
