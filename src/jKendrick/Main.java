package jKendrick;

import java.io.IOException;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;


public class Main {
	public static void main(String[] args) {
		double step = 0.1;
		double last = 20.;
		double[] arguments ={ 9999., 1.0, 0.0 };
		double [][] results = 	integratorExample(step, last, arguments);
		try {
			xchartExample(results, step, last);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private static double[][] integratorExample(double step, double last, 
																double[] args) {
		FirstOrderIntegrator integ;
		integ = new ClassicalRungeKuttaIntegrator(0.1);
		FirstOrderDifferentialEquations ode = new SIR_ODE(0.00042, .23);
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i;
		do {
			System.out.format("Conditions at time %.1f:  S:%.0f  I:%.0f  R:%.0f%n",
									t, args[0],  args[1], args[2]);
			i = (int)(t/step);
			for(int j = 0; j< nbArgs; ++j)
				results[j][i]= args[j];
			integ.integrate(ode, t, args, t + 0.1, args);
			t += step;
		} while (t <last);	
		return results;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void xchartExample(double[][] results, double step, double last) 
															throws IOException {
		double[] xData = new double[results[0].length];
		double[] yData = new double[results[0].length];
		for (int x = 0; x< (int) Math.ceil(last / step); ++x) {
			xData[x]= x;
			yData[x]= results[0][x];
		}
		// Create Chart
		XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)",
																	xData, yData);

		// Show it
		new SwingWrapper(chart).displayChart();

		// Save it
		BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG);

		// or save it in high-res
		BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI",
															BitmapFormat.PNG, 300);
	}
	
	
	
	private static class SIR_ODE implements FirstOrderDifferentialEquations{
		private double beta;
		private double gamma;
		
		public SIR_ODE(double beta, double gamma){
			this.beta = beta;
			this.gamma = gamma;
			}
		
		@Override
		public void computeDerivatives(double t, double[] sir, double[] sirDot)
				throws MaxCountExceededException, DimensionMismatchException {
			sirDot[0] = -beta * (sir[1] * sir[0]);
			sirDot[1] = beta * (sir[1] * sir[0]) - gamma * sir[1];
			sirDot[2] = gamma * sir[1];			
		}

		@Override
		public int getDimension() {
			return 3;
		}
	}		
}
