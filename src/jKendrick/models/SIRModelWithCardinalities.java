package jKendrick.models;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class SIRModelWithCardinalities implements FirstOrderDifferentialEquations {
	private final int N;
	private double beta;
	private double gamma;

			public SIRModelWithCardinalities(double beta, double gamma, int N){
				this.beta = beta;
				this.gamma = gamma;
				this.N = N;
				}
			
			private boolean theSumOfDerivativesIsNull(double[] sirDot) {
				double epsilon = 0.0000000000001;
				double totalDot = 0.0;
				for (double d : sirDot) 
					totalDot += d;
				return Math.abs(totalDot - 0.0) < epsilon;
			}

			@Override
			public void computeDerivatives(double t, double[] sir, double[] sirDot)
					throws MaxCountExceededException, DimensionMismatchException {
				sirDot[0] = (-beta * sir[1] * sir[0]);
				sirDot[1] = (beta * sir[1] * sir[0]) - (gamma * sir[1]);
				sirDot[2] = gamma * sir[1];
				
				assert theSumOfDerivativesIsNull(sirDot);
			}

		@Override
			public int getDimension() {
				return 3;
			}

}
