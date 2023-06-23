package jKendrick.scenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

import jKendrick.concerns.Concern;
import jKendrick.concerns.TransitionRateMatrix;

public class Scenario implements FirstOrderDifferentialEquations{
	private List<Concern> concerns;
	private Map<String,Double> parameters;
	private Map<String,Double> initialParameters;
	private List<String> compartments;
	
	public Scenario(List<Concern> concerns) {
		this.concerns=concerns;
		parameters=new HashMap<String,Double>();
		initialParameters=new HashMap<String,Double>();
		for (int i = 0; i < concerns.size(); ++i) {
			parameters.putAll(concerns.get(i).getParameters());
		}
		this.compartments=initCompartments();
	}
	
	public void saveInitialParams() {
		initialParameters.putAll(parameters);
	}
	
	public void resetParams() {
		parameters.putAll(initialParameters);
	}
	
	public List<String> getCompartments(){
		return compartments;
	}
	
	public void setParameter(String compartment,double population) {
		assert parameters.containsKey(compartment);
		parameters.put(compartment, population);
	}
	
	public void transition(String from, String to) {
		assert parameters.containsKey(from) && parameters.containsKey(to);
		double fromPop=parameters.get(from);
		double toPop=parameters.get(to);
		setParameter(from, fromPop-1.);
		setParameter(to, toPop+1.);
	}
	
	public int indexOf(String s) {
		return compartments.indexOf(s);
	}
	
	public int getNbCompartments() {
		return compartments.size();
	}
	
	public double getParam(String key) {
		return parameters.get(key);
	}
	
	public String getCompartment(int x) {
		return compartments.get(x);
	}
	
	
	public TransitionRateMatrix getTransitions() {
		//a modifier pour la composition a plusieurs concerns
		return concerns.get(0).getTransitionRateMatrix();
	}
	
	public List<String> initCompartments(){
		return concerns.get(0).getCompartments();
	}
	
	public double getN() {
		double n=0.;
		for(int i=0;i<getNbCompartments();++i) {
			n+=getParam(compartments.get(i));
		}
		return n;
	}
	
	public double[] getPopulation() {
		double[] population=new double[getNbCompartments()];
		double n=getN();
		assert n>0.;
		for(int i=0;i<getNbCompartments();++i) {
			population[i]=getParam(compartments.get(i));
		}
		return population;
	}
	
	public double[] getProportions() {
		double[] proportion=new double[getNbCompartments()];
		double n=getN();
		assert n>0.;
		for(int i=0;i<getNbCompartments();++i) {
			proportion[i]=getParam(compartments.get(i))/n;
		}
		return proportion;
	}
	
	@Override
	public void computeDerivatives(double t, double[] population, double[] populationDoT)
			throws MaxCountExceededException, DimensionMismatchException {
		for(int i=0;i<getNbCompartments();++i) {
			populationDoT[i]=0.;
			for(int j=0;j<getNbCompartments();++j) {
				if(j!=i) {
					populationDoT[i]+=getTransitions().getRate(compartments.get(j), compartments.get(i), this);
					populationDoT[i]-=getTransitions().getRate(compartments.get(i), compartments.get(j), this);
				}
			}
		}
	}

	@Override
	public int getDimension() {
		return getNbCompartments();
	}
	

}
