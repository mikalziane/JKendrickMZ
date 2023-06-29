package jKendrick.simulation;

public interface ISolver {
	public void solve();

	public double[][][] getResult();

	public double[][] getMedianPath();

	public String[] getLabels();

	public double getEnd();

	public double getStep();
	
	public double[] getTimes(int cycle);
	
	public double[] getMedianTimes();
	
	public int getNbSteps();
}
