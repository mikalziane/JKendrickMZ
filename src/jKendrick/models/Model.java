package jKendrick.models;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public abstract class Model implements FirstOrderDifferentialEquations{
	protected String[] compartmentNames;
	protected String[] parameterNames;
	protected double[] parameters;

	public Model(String cNames, String pNames) {
		compartmentNames = cNames.split(" ");
		parameterNames = pNames.split(" ");
		parameters = new double[parameterNames.length];
	}

	public int getNBCompartments() { return compartmentNames.length; }
	public int getNBParamaters() { return parameterNames.length; }
	
	private int getIndex(String name) {
		for (int i = 0; i < parameterNames.length; ++i)
			if (name.contentEquals(parameterNames[i]))
				return i;
		throw new IllegalArgumentException("Inavalid parameter name : " + name);
	}
	
	public double get(String name) {
		return parameters[getIndex(name)];
	}
	
	public void set(String name, double value) {
		parameters[getIndex(name)] = value;
	}

	public String getCompartmentName(int i) {
		assert i >= 0 && i < this.getNBCompartments();
		return this.compartmentNames[i];
	}

	public boolean theSumOfDerivativesIsNull(double[] dot) {
		double epsilon = 0.0000000000001;
		double sum = 0.0;
		for (double d : dot) 
			sum += d;
		return Math.abs(sum - 0.0) < epsilon;
	}

	@Override
	public int getDimension() {
		return getNBCompartments();
	}

}