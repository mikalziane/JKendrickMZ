package sandBox;

import java.io.IOException;

import jKendrick.IHM.Visualization;

public class Main {
	public static void main(String[] args) throws IOException {
		double results[][] = new double[3][100];
		double d;
		
		for (int i =1; i<100; ++i) {
			results[0][i] = 999- i *i /10;
			results[1][i] = Math.abs(i*i/10 - i);
			results[2][i] = Math.abs(1000 - results[0][i] -results[1][i]);
			System.out.println(results[0][i] + " "+ results[1][i] + " " +results[2][i]);
		}
		String[] names = {"S", "I", "R"};
		Visualization.xchartExample(results, 1., 99., names, "Essai", "Time", "Pop");

	}

}
