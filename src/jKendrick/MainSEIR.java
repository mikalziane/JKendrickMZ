package jKendrick;



import java.io.IOException;

import jKendrick.models.SEIR;
import jKendrick.solvers.RK4Solver;


public class MainSEIR {
	public static void main(String[] args) {
		double step = 1;
		double last = 21900.;
		
		String[] seriesNames = {"S", "E", "I", "R"};
		String title = "SEIR Model";
		String xAxis = "Time (days)";
		String yAxis = "Proportion of individuals";
		
		
		double s0 = 0.1; // initial proportion of Ss
		double e0 = 0.0001; // initial proportion of Es
		double i0 = 0.0001; // initial proportion of Is
		double r0 = 0.898; // initial proportion of Rs
		double[] arguments ={ s0, e0, i0, r0};
		
		//final double sommeProportionCompartiments = s0 + e0 + i0 + r0;
		
		//assert sommeProportionCompartiments == 1 : "La somme des proportions des populations au sein des compartiments vaut 1" ;
			
		
		double [][] results = 	integratorExample(step, last, arguments);
		Visualization viz = new Visualization();
		
		try {
			viz.xchartExample(results, step, last, seriesNames, title, xAxis, yAxis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double[][] integratorExample(double step, double last,
			double[] args) {
		RK4Solver rk4 = new RK4Solver(step);
		SEIR ode = new SEIR(1.4247, 0.14286, 0.0000391, 0.07143);
		
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		
		final double THRESHOLD = 0.001;
		
		do {
			System.out.format("Conditions at time %.1f:  S:%.1f E:%.1f I:%.1f R:%.1f%n",
					t, args[0],  args[1], args[2], args[3]);
			i = (int)(t/step);
			
			double sommeCompartiments = (args[0] + args[1] + args[2] + args[3]);
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
