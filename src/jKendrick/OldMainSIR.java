package jKendrick;

import java.io.IOException;

import jKendrick.models.old.OldSIRModel;
import jKendrick.solvers.RK4Solver;


public class OldMainSIR {
	public static void main(String[] args) {
		double step = 1;
		double last = 70.;
		
		String[] seriesNames = {"S", "I", "R"};
		String title = "SIR Model";
		String xAxis = "Time (days)";
		String yAxis = "Proportion of individuals";
		
		double[] arguments ={ 0.999999, 0.000001, 0.0};
		double [][] results = 	integratorExample(step, last, arguments);
		Visualization viz = new Visualization ();
		
		try {
			viz.xchartExample(results, step, last, seriesNames, title, xAxis, yAxis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double[][] integratorExample(double step, double last,
			double[] args) {
		RK4Solver rk4 = new RK4Solver(step);
		OldSIRModel ode = new OldSIRModel(1.4247, 0.14286);
		
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		
		final double THRESHOLD = 0.001;
		
		do {
			
			System.out.format("Conditions at time %.1f:  S:%.1f  I:%.1f  R:%.1f%n",
					t, args[0],  args[1], args[2]);
			i = (int)(t/step);
			
			double sommeCompartiments = (args[0] + args[1] + args[2]);
			assert (Math.abs(1 - sommeCompartiments) < THRESHOLD)  : "La somme des proportions des populations au sein des compartiments vaut bien 1.";
			
			for(int j = 0; j< nbArgs; ++j)
				results[j][i]= args[j];
			rk4.integrate(ode, t, args, t + step, args);
			t += step;
			++i;
			
		} while (t <last);	
		return results;
	}

	
}
