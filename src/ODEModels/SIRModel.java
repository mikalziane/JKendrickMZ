package ODEModels;

import org.apache.commons.math3.exception.DimensionMismatchException;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;


// Inspired by https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.1/index.html
public class SIRModel implements FirstOrderDifferentialEquations {
	
	
		//private double N;
		private double beta;
		private double gamma;

		//public SIRModel(double beta, double gamma, double N){
		public SIRModel(double beta, double gamma){
			this.beta = beta;
			this.gamma = gamma;
			}

		@Override
		public void computeDerivatives(double t, double[] sir, double[] sirDot)
				throws MaxCountExceededException, DimensionMismatchException {
		/*	sirDot[0] = ((-beta * sir[1] * sir[0])/N);
			sirDot[1] = ((beta * sir[1] * sir[0])/N) - (gamma * sir[1]);
			sirDot[2] = gamma * sir[1];*/
			
			sirDot[0] = (-beta * sir[1] * sir[0]);
			sirDot[1] = (beta * sir[1] * sir[0]) - (gamma * sir[1]);
			sirDot[2] = gamma * sir[1];
			
		}

	@Override
		public int getDimension() {
			return 3;
		}

}
