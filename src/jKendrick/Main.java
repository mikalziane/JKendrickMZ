package jKendrick;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;


public class Main {
	public static void main(String[] args) {
		double step = 1;
		double last = 70.;
		double[] arguments ={ 999999.0, 1.0, 0.0 };
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
		integ = new ClassicalRungeKuttaIntegrator(1);
		FirstOrderDifferentialEquations ode = new SIR_ODE(1.4247, 0.14286, 1000000);
		int nbArgs = args.length;
		int duration = (int) Math.ceil(last / step);
		double[][] results = new double[nbArgs][duration];
		double t = 0.0;
		int i = 0;
		do {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void xchartExample(double[][] results, double step, double last)
															throws IOException {
		double[] xData = new double[results[0].length];
		
		double[][] yData = new double[results.length][results[0].length];
		
		
	for (int k = 0; k < results.length; ++k) {	
		for (int i = 0; i< (int) Math.ceil(last / step); ++i) {
			xData[i]= i * step;
			yData[k][i] = results[k][i];
			}
		}
				
	// Create Chart
	final XYChart chart = new XYChartBuilder().width(600).height(400).theme(ChartTheme.XChart).title("SIR Model").xAxisTitle("Time (year)").yAxisTitle("Number of individuals").build();

	// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);

	// Series
		chart.addSeries("S", xData, yData[0]);
		chart.addSeries("I", xData, yData[1]);
		chart.addSeries("R", xData, yData[2]);
						

	// Schedule a job for the event-dispatching thread:
	// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {

		// Create and set up the window.
		JFrame frame = new JFrame("Deterministic model");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// chart
	    JPanel chartPanel = new XChartPanel<XYChart>(chart);
		frame.add(chartPanel, BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
				 }
			});

	}



	private static class SIR_ODE implements FirstOrderDifferentialEquations{
		private double N;
		private double beta;
		private double gamma;

		public SIR_ODE(double beta, double gamma, double N){
			this.beta = beta;
			this.gamma = gamma;
			this.N = N;
			}

		@Override
		public void computeDerivatives(double t, double[] sir, double[] sirDot)
				throws MaxCountExceededException, DimensionMismatchException {
			sirDot[0] = ((-beta * sir[1] * sir[0])/N);
			sirDot[1] = ((beta * sir[1] * sir[0])/N) - (gamma * sir[1]);
			sirDot[2] = gamma * sir[1];
		}

	@Override
		public int getDimension() {
			return 3;
		}
	}

	
}
