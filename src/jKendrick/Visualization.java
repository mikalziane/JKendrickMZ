package jKendrick;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Visualization {
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void xchartExample(double[][] results, double step, double last)
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


}
