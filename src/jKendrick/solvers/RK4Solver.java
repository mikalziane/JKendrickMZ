package jKendrick.solvers;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import jKendrick.scenario.Scenario;

public class RK4Solver {
	private FirstOrderIntegrator integ;
	
	public RK4Solver(double step) {
		integ = new ClassicalRungeKuttaIntegrator(step);
	}
	
	public double integrate (FirstOrderDifferentialEquations ode, 
					double t0, double[] y0, double t, double[] y) {
		return integ.integrate(ode, t0, y0, t , y);
	}
	
	public double[][] solve(double step,double end,Scenario s){
		double t=0.;
		int nbStep=(int) Math.ceil(end/step);
		double[][] result=new double[nbStep][s.getNbCompartments()];
		for(int i=0;t<end;t+=step, ++i) {
			double[] population=s.getPopulation();
			result[i]=population;
			integrate(s, t, population, t+step, population);
			for(int j=0;j<s.getNbCompartments();++j) {
				s.setParameter(s.getCompartment(j), population[j]);
			}
		}
		return result;
	}
}
