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
	public void xchartExample(double[][] results, double step, double last, String[] seriesNames, String title, String xAxis, String yAxis)
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
	final XYChart chart = new XYChartBuilder().width(500).height(400).theme(ChartTheme.XChart).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();

	// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);

	// Series
		for (int i = 0; i < seriesNames.length; i++) {
		    chart.addSeries(seriesNames[i],xData, yData[i]);
		}						
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
