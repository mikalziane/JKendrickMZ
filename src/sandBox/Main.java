package sandBox;

import java.io.IOException;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import jKendrick.IHM.Visualization;
import jKendrick.core.ISolver;

public class Main {
	private static double results[][];
	private static final String[] names = {"S", "I", "R"};
	
	public static void main(String[] args) throws IOException {
		//xchartExample();
		//simpleChart();
		 getChartExample();
	}
	
	public static void initializeResults() {
		results = new double[3][100];
		for (int i =1; i<100; ++i) {
			results[0][i] = 999- i *i /10;
			results[1][i] = Math.abs(i*i/10 - i);
			results[2][i] = Math.abs(1000 - results[0][i] -results[1][i]);
			System.out.println(results[0][i] + " "+ results[1][i] + " " +results[2][i]);
		}
	}
	
	public static void xchartExample() throws IOException {
		initializeResults();
		Visualization.xchartExample(results, 1., 99., names, "Essai", "Time", "Population");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void simpleChart() {
		double[] xData = new double[] { 0.0, 1.0, 2.0 };
	    double[] yData = new double[] { 2.0, 1.0, 0.0 };

	    // Create Chart
	    XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

	    // Show it
	    new SwingWrapper(chart).displayChart();
	}
	
	public static void getChartExample() throws IOException {
		ISolver solver = new ISolver() {
			private double step;
			private double end;
			private int nbSteps;
			
			public ISolver initialise(double step, double end, int nbSteps) {
				this.step = step;
				this.end = end;
				this.nbSteps = nbSteps;
				initializeResults();
				return this;
			}
			@Override
			public void solve() {
				
			}

			@Override
			public double[][][] getResult(){
				double r[][][]=new double[1][nbSteps][results.length];
					for(int i=0;i<nbSteps;++i) {
						for(int j=0;j<results.length;++j) {
							r[0][i][j]=results[j][i];
						}
					}
				return r;
			}
			@Override
			public double[][] getMedianPath() {
				return getResult()[0];
			}

			@Override
			public String[] getLabels() {
				return names;
			}

			@Override
			public double getEnd() {
				return end;
			}

			@Override
			public double getStep() {
				return step;
			}

			@Override
			public double[] getTimes(int cycle) {
				double[] times=new double[nbSteps];
				times[0]=0;
				for(int i=1;i<times.length;++i) {
					times[i]=times[i-1]+step;
				}
				return times;
			}

			@Override
			public double[] getMedianTimes() {
				return getTimes(1);
			}

			@Override
			public int getNbSteps() {
				return nbSteps;
			}
			
		}.initialise(1., 99., 100);
		new Visualization().getChart(solver, "getChartExample", "Time", "Population");
	}

}
