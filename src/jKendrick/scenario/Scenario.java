package jKendrick.scenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jKendrick.concerns.Concern;
import jKendrick.concerns.TransitionRateMatrix;

public class Scenario {
	private List<Concern> concerns;
	private Map<String,Double> parameters;
	private List<String> compartments;
	
	public Scenario(List<Concern> concerns) {
		this.concerns=concerns;
		parameters=new HashMap<>();
		for (int i = 0; i < concerns.size(); ++i) {
			parameters.putAll(concerns.get(i).getParameters());
		}
		this.compartments=initCompartments();
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
	
	
	public TransitionRateMatrix getTransitions() {
		//a modifier pour la composition a plusieurs concerns
		return concerns.get(0).getTransitionRateMatrix();
	}
	
	public List<String> initCompartments(){
		return concerns.get(0).getCompartments();
	}
	
	

}
