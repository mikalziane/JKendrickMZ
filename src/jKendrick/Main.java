package jKendrick;



import java.io.IOException;


import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import ODEModels.SIRModel;


public class Main {
	public static void main(String[] args) {
		double step = 1;
		double last = 70.;
		/* S, I et R parameters for the SIR model without demography
		double[] arguments ={ 0.999999, 0.000001, 0.0 }; */
		double[] arguments ={ 0.999999, 0.000001, 0.0};
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
		FirstOrderIntegrator integ;
		integ = new ClassicalRungeKuttaIntegrator(step);
		SIRModel ode = new SIRModel(1.4247, 0.14286);
		//SIRModelWithDemography ode = new SIRModelWithDemography (1.4247, 0.14286, 0.0000391);
		//SISModel ode = new SISModel(1.4247, 0.14286);
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		do {
			//System.out.format("Conditions at time %.1f:  S:%.1f  I:%.1f  R:%.1f%n",
			System.out.format("Conditions at time %.1f:  S:%.1f  I:%.1f  R:%.1f%n",
					t, args[0],  args[1], args[2]);
			i = (int)(t/step);
			for(int j = 0; j< nbArgs; ++j)
				results[j][i]= args[j];
			integ.integrate(ode, t, args, t + step, args);
			t += step;
			++i;
		} while (t <last);	
		return results;
	}

	
}
