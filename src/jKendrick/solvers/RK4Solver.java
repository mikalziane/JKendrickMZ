package jKendrick.solvers;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

public class RK4Solver {
	private FirstOrderIntegrator integ;
	
	public RK4Solver(double step) {
		integ = new ClassicalRungeKuttaIntegrator(step);
	}
	
	public double integrate (FirstOrderDifferentialEquations ode, 
					double t0, double[] y0, double t, double[] y) {
		return integ.integrate(ode, t0, y0, t , y);
	}
}
