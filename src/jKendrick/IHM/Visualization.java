package jKendrick.IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import org.knowm.xchart.style.markers.SeriesMarkers;

import jKendrick.core.ISolver;

public class Visualization {
	
	
	public static void xchartExample(double[][] results, double step, double last, 
			String[] seriesNames, String title, String xAxisTitle, String yAxisTitle)
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
	final XYChart chart = new XYChartBuilder().width(500).height(400)
			.theme(ChartTheme.XChart).title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build();

		// Customize Chart
		//chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);

		
		// Customize chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		//chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setMarkerSize(3);
		
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

	
	public void getChart(ISolver solver,String title, String xAxisTitle, String yAxisTitle)
			throws IOException {
		double[][][] results=solver.getResult();
		int nbSteps=solver.getNbSteps();
		String[] labels=solver.getLabels();
		final XYChart chart = new XYChartBuilder().width(500).height(400)
				.title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build();
		
		//style chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setMarkerSize(1);
		
		
		if(results.length>1) {
			double[][] xDataSet=new double[results.length][nbSteps];
			double[][][] yDataSet=new double[results.length][labels.length][nbSteps];
			for(int i=0;i<results.length;++i) {	
				for(int j=0;j<labels.length;++j) {	
					for(int k=0;k<results[0].length;++k) {
						xDataSet[i]=solver.getTimes(i);
						yDataSet[i][j][k]=results[i][k][j];
					}
					String serieName=new String(" "+labels[j]+"-"+i);
					
					XYSeries stochasticSeries =chart.addSeries(serieName,xDataSet[i], yDataSet[i][j]);
					stochasticSeries.setLineColor(new Color(0, 0, 0, 10));
					stochasticSeries.setMarkerColor(new Color(0, 0, 0, 10));
					stochasticSeries.setMarker(SeriesMarkers.CIRCLE);
					stochasticSeries.setShowInLegend(false);
				}	
			}
		}
		double[][] medianPath=solver.getMedianPath();
		
		double[] xMedianPath=new double[medianPath.length];
		double[][] yMedianPath=new double[labels.length][medianPath.length];
		for(int iLabel=0;iLabel<labels.length;++iLabel) {
			for(int j=0;j<medianPath.length;++j) {
				xMedianPath=solver.getMedianTimes();
				yMedianPath[iLabel][j]=medianPath[j][iLabel];
			}
			chart.addSeries(labels[iLabel], xMedianPath, yMedianPath[iLabel]);
			
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

			// Create and set up the window.
			JFrame frame = new JFrame("Modelisation");
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
