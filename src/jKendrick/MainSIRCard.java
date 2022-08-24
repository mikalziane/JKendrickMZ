package jKendrick;

import java.io.IOException;

import jKendrick.models.SIR;
import jKendrick.solvers.RK4Solver;

public class MainSIRCard {
	public static void main(String[] args) {
		SIR model = new SIR();
		model.set("beta", 1.4247);
		model.set("gamma", 0.14286);
		
		double step = 1;
		double last = 70.;
		
		int[] compartments ={999999, 1, 0};
		int N = 0;
		for (int x : compartments)
			N += x;
		
		double[]arguments = new double[compartments.length];
		for (int i = 0; i < compartments.length; ++i)
			arguments[i] = (double)compartments[i] / N;
		
		RK4Solver solver = new RK4Solver(step);

		
		double [][] results = 	runSolver(step, last, arguments, model, solver);
		Visualization viz = new Visualization ();
		
		try {
			viz.xchartExample(results, step, last);;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String valuesAtTime(SIR model, double t, double[] args) {
		StringBuilder sb = new StringBuilder(String.format("Conditions at time %.1f: ", t));
		for (int j = 0; j < model.getNBCompartments(); ++j)
			sb.append(String.format("%s:%.10f ", model.getCompartmentName(j), args[j]));	
		return sb.toString();
	}
	
	private static double[][] runSolver(double step, double last, 
			double[] args, SIR model, RK4Solver solver) {
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		
		do {
			System.out.println(valuesAtTime(model, t, args).toString());
			i = (int)(t/step);
			for(int j = 0; j< nbArgs; ++j)
				results[j][i]= args[j];
			solver.integrate(model, t, args, t + step, args);
			t += step;
			++i;
		} while (t <last);	
		return results;
	}
}
