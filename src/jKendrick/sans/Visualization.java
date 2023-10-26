package jKendrick.sans;

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

import java.io.IOException;

public class Visualization {

    public void getChart(ISolver solver, String title, String xAxis, String yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).theme(ChartTheme.Matlab).build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

        // Generate data from your solver
        double[] xData = solver.getTimes(0);  // Assuming cycle 0, you can change this
        double[][] medianPath = solver.getMedianPath();
        double[] yData = medianPath[0];  // Assuming you want to plot the first path, you can change this

        // Add series to chart
        XYSeries series = chart.addSeries("Series1", xData, yData);
        series.setMarker(SeriesMarkers.NONE);

        // Display the chart
        JFrame frame = new JFrame("XChart");
        JPanel chartPanel = new XChartPanel<>(chart);
        frame.add(chartPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}

