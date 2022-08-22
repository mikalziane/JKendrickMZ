package jKendrick;

import java.io.IOException;

import jKendrick.models.SIRModel;
import jKendrick.solvers.RK4Solver;

public class MainSIRCard {
	public static void main(String[] args) {
		double step = 1;
		double last = 70.;
		
		int[] compartments ={ 999999, 1, 0};
		int N = 0;
		for (int x : compartments)
			N += x;
		
		double[]arguments = new double[compartments.length];
		for (int i = 0; i < compartments.length; ++i)
			arguments[i] = (double)compartments[i] / N;
		
		double [][] results = 	integratorExample(step, last, arguments);
		Visualization viz = new Visualization ();
		
		try {
			viz.xchartExample(results, step, last);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double[][] integratorExample(double step, double last,
			double[] args) {
		RK4Solver rk4 = new RK4Solver(step);
		SIRModel ode = new SIRModel(1.4247, 0.14286);
		
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		
		final double THRESHOLD = 0.00000000001;
		
		do {
			
			System.out.format("Conditions at time %.1f:  S:%.10f  I:%.10f  R:%.10f%n",
					t, args[0],  args[1], args[2]);
			i = (int)(t/step);
			
			double sommeCompartiments = (args[0] + args[1] + args[2]);
			assert (Math.abs(1 - sommeCompartiments) < THRESHOLD)  : "La somme des proportions doit valoir 1.";
			
			for(int j = 0; j< nbArgs; ++j)
				results[j][i]= args[j];
			rk4.integrate(ode, t, args, t + step, args);
			t += step;
			++i;
			
		} while (t <last);	
		return results;
	}

	
}
