package jKendrick.models;

import org.apache.commons.math3.exception.DimensionMismatchException;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;


// Inspired by https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.6/index.html
public class SEIRModel implements FirstOrderDifferentialEquations {
	
	
		
		private double beta;
		private double gamma;
		private double mu;
		private double sigma;

		public SEIRModel(double beta, double gamma, double mu, double sigma){
			this.beta = beta;
			this.gamma = gamma;
			this.mu = mu;
			this.sigma = sigma;
			}

		@Override
		public void computeDerivatives(double t, double[] seir, double[] seirDot)
				throws MaxCountExceededException, DimensionMismatchException {
		
			
			seirDot[0] = mu - ((beta * seir[2] + mu) * seir[0]);
			seirDot[1] = (beta * seir[2] * seir[0]) - ((mu + sigma) * seir[1]);
			seirDot[2] = (sigma * seir[1]) - ((mu + gamma) * seir[2]);
			seirDot[3] = (gamma * seir[2]) - (mu * seir[3]);
			
		}

	@Override
		public int getDimension() {
			return 4;
		}

}
