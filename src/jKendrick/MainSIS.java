package jKendrick;


import java.io.IOException;

import jKendrick.models.SISModel;
import jKendrick.solvers.RK4Solver;


public class MainSIS {
	public static void main(String[] args) {
		double step = 1;
		double last = 70.;
		
		double[] arguments ={ 0.999999, 0.000001};
		double [][] results = 	integratorExample(step, last, arguments);
		VisualizationSIS viz = new VisualizationSIS ();
		
		try {
			viz.xchartExample(results, step, last);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double[][] integratorExample(double step, double last,
			double[] args) {
		RK4Solver rk4 = new RK4Solver(step);
		
		SISModel ode = new SISModel(1.4247, 0.14286);
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		
		final double THRESHOLD = 0.001;
		
		do {
			
			System.out.format("Conditions at time %.1f:  S:%.1f  I:%.1f.%n",
					t, args[0],  args[1]);
			i = (int)(t/step);
			
			double sommeCompartiments = (args[0] + args[1]);
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

