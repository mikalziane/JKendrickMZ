package jKendrick.scenario;

public interface ISolver {
	public void solve();

	public double[][][] getResult();

	public double[][] getMedianPath();

	public String[] getLabels();

	public double getEnd();

	public double getStep();
}
