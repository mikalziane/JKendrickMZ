package jKendrick.solvers;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import jKendrick.simulation.ISolver;
import jKendrick.simulation.Model;
import jKendrick.simulation.Scenario;

public class RK4Solver implements ISolver{
	private FirstOrderIntegrator integ;
	private double[][] result;
	private double step;
	private double end;
	private int nbSteps;
	private Scenario s;
	
	public RK4Solver(Model model) {
		this.step=model.getStep();
		this.end=model.getEnd();
		this.s=model.getScenario();
		this.nbSteps=(int) Math.ceil(end/step);
		integ = new ClassicalRungeKuttaIntegrator(step);
	}
	
	@Deprecated
	public RK4Solver(double step) {
		this.step=step;
	}
	
	
	
	public double integrate (FirstOrderDifferentialEquations ode, 
					double t0, double[] y0, double t, double[] y) {
		return integ.integrate(ode, t0, y0, t , y);
	}
	
	@Override
	public void solve(){
		s.saveInitialParams();
		double t=0.;
		double[][] result=new double[nbSteps][s.getNbCompartments()];
		for(int i=0;t<end;t+=step, ++i) {
			double[] population=s.getPopulation();
			result[i]=population;
			integrate(s, t, population, t+step, population);
			for(int j=0;j<s.getNbCompartments();++j) {
				s.setParameter(s.getCompartment(j), population[j]);
			}
		}
		this.result=result;
		s.resetParams();
	}
	
	@Override
	public double[][][] getResult(){
		double r[][][]=new double[1][nbSteps][s.getNbCompartments()];
			for(int i=0;i<nbSteps;++i) {
				for(int j=0;j<s.getNbCompartments();++j) {
					r[0][i][j]=result[i][j];
				}
			}
		return r;
	}

	@Override
	public double[][] getMedianPath() {
		return getResult()[0];
	}

	@Override
	public String[] getLabels() {
		return s.getCompartments().toArray(new String[s.getNbCompartments()]);
	}

	@Override
	public double getEnd() {
		return end;
	}

	@Override
	public double getStep() {
		return step;
	}

	@Override
	public double[] getTimes(int cycle) {
		double[] times=new double[nbSteps];
		times[0]=0;
		for(int i=1;i<times.length;++i) {
			times[i]=times[i-1]+step;
		}
		return times;
	}

	@Override
	public double[] getMedianTimes() {
		return getTimes(1);
	}

	@Override
	public int getNbSteps() {
		return nbSteps;
	}
	
	
	
}
