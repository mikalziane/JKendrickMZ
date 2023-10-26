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
	
	
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void xchartExample(double[][] results, double step, double last, 
			String[] seriesNames, String title, String xAxis, String yAxis)
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
			.theme(ChartTheme.XChart).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();

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
	
	public void deterministicChart(double[][] results, double step, double last, String[] seriesNames, String title, String xAxis, String yAxis)
			throws IOException {
		double[] xData = new double[results.length];
		double[][] yData = new double[results[0].length][results.length];

		for (int k = 0; k < results[0].length; ++k) {	
			for (int i = 0; i< (int) Math.ceil(last / step); ++i) {
				xData[i]= i * step;
				yData[k][i] = results[i][k];
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
	
	public void stochasticChart(double[][][] results, double[][] average, String[] seriesNames,String title, String xAxis, String yAxis)
			throws IOException {
		double[][] xData=new double[results.length][results[0].length];
		double[][][] yData=new double[results.length][(results[0][0].length)-1][results[0].length];
		//create chart
		final XYChart chart = new XYChartBuilder().width(500).height(400).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
		//style chart
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setMarkerSize(1);
		//pour chaque cycle
		
		for(int i=0;i<results.length;++i) {
			//pour chaque compartiment
			for(int j=0;j<(results[0][0].length)-1;++j) {
				
				for(int k=0;k<results[0].length;++k) {
					xData[i][k]=results[i][k][(results[0][0].length)-1];
					yData[i][j][k]=results[i][k][j];
					
				}
				String serieName=new String(seriesNames[j]+"-"+i);
				
				XYSeries stochasticSeries =chart.addSeries(serieName,xData[i], yData[i][j]);
				stochasticSeries.setLineColor(new Color(0, 0, 0, 10));
				stochasticSeries.setMarkerColor(new Color(0, 0, 0, 10));
				stochasticSeries.setMarker(SeriesMarkers.CIRCLE);
				stochasticSeries.setShowInLegend(false);
				
			}
		}
		double[] xDataAverage=new double[average.length];
		double[][] yDataAverage=new double[(average[0].length)-1][average.length];
		for(int i=0;i<(average[0].length)-1;++i) {
			for(int j=0;j<average.length;++j) {
				xDataAverage[j]=average[j][(average[0].length)-1];
				yDataAverage[i][j]=average[j][i];
			}
			chart.addSeries(seriesNames[i], xDataAverage, yDataAverage[i]);
			//series.setMarkerColor(Color.RED);
			//series.setMarker(SeriesMarkers.CIRCLE);
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

			// Create and set up the window.
			JFrame frame = new JFrame("Stochastic model");
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
	
	public void stochasticChart(double step,double[][][] results, double[][] average,
			String[] seriesNames,String title, String xAxis, String yAxis)throws IOException  {
		double[] xData=new double[results[0].length];
		double[][][] yData=new double[results.length][results[0][0].length][results[0].length];
		final XYChart chart = new XYChartBuilder().width(500).height(400)
				.title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setMarkerSize(1);
		
		
		for(int k=0;k<results[0].length;++k) {
			xData[k]=step*k;
		}
		
		for(int i=0;i<results.length;++i) {
			//pour chaque compartiment
			for(int j=0;j<(results[0][0].length);++j) {
				
				for(int k=0;k<results[0].length;++k) {
					
					yData[i][j][k]=results[i][k][j];
					
				}
				String serieName=new String(seriesNames[j]+"-"+i);
				
				XYSeries stochasticSeries =chart.addSeries(serieName,xData, yData[i][j]);
				stochasticSeries.setLineColor(new Color(0, 0, 0, 10));
				stochasticSeries.setMarkerColor(new Color(0, 0, 0, 10));
				stochasticSeries.setMarker(SeriesMarkers.CIRCLE);
				stochasticSeries.setShowInLegend(false);
				
				
				
			}
		}
		
		double[][] yDataAverage=new double[average[0].length][average.length];
		for(int i=0;i<average[0].length;++i) {
			for(int j=0;j<average.length;++j) {
				
				yDataAverage[i][j]=average[j][i];
			}
			chart.addSeries(seriesNames[i], xData, yDataAverage[i]);
			
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

			// Create and set up the window.
			JFrame frame = new JFrame("Stochastic model");
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
	
	public void getChart(ISolver solver,String title, String xAxis, String yAxis)
			throws IOException {
		double[][][] results=solver.getResult();
		int nbSteps=solver.getNbSteps();
		String[] labels=solver.getLabels();
		final XYChart chart = new XYChartBuilder().width(500).height(400)
				.title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
		//style chart
		
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setMarkerSize(1);
		
		if(results.length>1) {
			double[][] xData=new double[results.length][nbSteps];
			double[][][] yData=new double[results.length][labels.length][nbSteps];
			for(int i=0;i<results.length;++i) {	
				for(int j=0;j<labels.length;++j) {	
					for(int k=0;k<results[0].length;++k) {
						xData[i]=solver.getTimes(i);
						yData[i][j][k]=results[i][k][j];
					}
					String serieName=new String(" "+labels[j]+"-"+i);
					
					XYSeries stochasticSeries =chart.addSeries(serieName,xData[i], yData[i][j]);
					stochasticSeries.setLineColor(new Color(0, 0, 0, 10));
					stochasticSeries.setMarkerColor(new Color(0, 0, 0, 10));
					stochasticSeries.setMarker(SeriesMarkers.CIRCLE);
					stochasticSeries.setShowInLegend(false);
				}	
			}
		}
		double[][] medianPath=solver.getMedianPath();
		
		double[] xDataAverage=new double[medianPath.length];
		double[][] yDataAverage=new double[labels.length][medianPath.length];
		for(int i=0;i<labels.length;++i) {
			for(int j=0;j<medianPath.length;++j) {
				xDataAverage=solver.getMedianTimes();
				yDataAverage[i][j]=medianPath[j][i];
			}
			chart.addSeries(labels[i], xDataAverage, yDataAverage[i]);
			
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
