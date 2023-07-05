package jKendrick.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;



public class Scenario implements FirstOrderDifferentialEquations{
	private List<Concern> concerns;
	private Map<String,Double> parameters;
	private Map<String,Double> initialParameters;
	private List<Map<String,String>> compartments;
	private List<String> attributes;
	
	public Scenario(List<Concern> concerns) {
		assert concerns.size()>0;
		this.concerns=concerns;
		parameters=new HashMap<String,Double>();
		initialParameters=new HashMap<String,Double>();
		attributes=new ArrayList<String>();
		for (int i = 0; i < concerns.size(); ++i) {
			parameters.putAll(concerns.get(i).getParameters());
			attributes.add(concerns.get(i).getName());
		}
		compartments=new ArrayList<Map<String,String>>();
		initCompartments();
		putCompartsInParams();
	}
	
	private void putCompartsInParams() {
		for(int i=0;i<compartments.size();++i) {
			parameters.put(mapToStringName(compartments.get(i)), 0.);
		}
	}
	
	public void setPopulation(String name, double pop) {
		setParameter(name, pop);
		Map<String,String> map=stringToMapName(name);
		double cumulPop=0.;
		for(int i=0;i<attributes.size();++i) {
			String attribute=attributes.get(i);
			String value=map.get(attributes.get(i));
			Map<String,String> cumulMap=new HashMap<String,String>();
			cumulMap.put(attribute, value);
			for(int j=0;j<compartments.size();++j) {
				if(compartments.get(j).get(attribute).equals(value)) {
					cumulPop+=getParam(mapToStringName(compartments.get(j)));
				}
			}
			setParameter(mapToStringName(cumulMap), cumulPop);
		}
	}
	
	
	

	public void saveInitialParams() {
		initialParameters.putAll(parameters);
	}
	
	public void resetParams() {
		parameters.putAll(initialParameters);
	}
	
	public List<String> getCompartments(){
		List<String> comparts=new ArrayList<>();
		for(int i=0;i<compartments.size();++i) {	
			comparts.add(mapToStringName(compartments.get(i)));
		}
		return comparts;
	}
	
	public void setParameter(String compartment,double population) {
		assert parameters.containsKey(compartment) || compartments.contains(stringToMapName(compartment));
		parameters.put(compartment, population);
	}
	
	public void printStringNames() {
		for(int i=0;i<compartments.size();++i) {
			System.out.println(mapToStringName(compartments.get(i)));
		}
	}
	
	public void transition(Map<String,String> from, Map<String,String> to) {
		assert parameters.containsKey(mapToStringName(from)) && parameters.containsKey(mapToStringName(to));
		double fromPop=parameters.get(mapToStringName(from));
		double toPop=parameters.get(mapToStringName(to));
		setParameter(mapToStringName(from), fromPop-1.);
		setParameter(mapToStringName(to), toPop+1.);
	}
	
	public int indexOf(Map<String,String> compartment) {
		int i=0;
		int n=-1;
		while(n==-1 && i<getNbCompartments()) {
			if(equivalence(compartment, getCompartment(i))) {
				n=i;
			}
			++i;
		}
		return n;
	}
	
	public int getNbCompartments() {
		return compartments.size();
	}
	
	public String mapToStringName(Map<String,String> name){
		StringBuilder s=new StringBuilder();
		for(Map.Entry<String, String> entry : name.entrySet()) {
			s.append(entry.getKey());
			s.append(':');
			s.append(entry.getValue());
			s.append('_');
		}
		return s.toString();
	}
	
	public Map<String,String> stringToMapName(String name){
		String[] items=name.split("_");
		Map<String,String> mapName=new HashMap<String,String>();
		for(int i=0;i<items.length-1;++i) {
			String[] keyValue=items[i].split(":");
			mapName.put(keyValue[0], keyValue[1]);
		}
		return mapName;
	}
	
	public boolean equivalence(Map<String,String> m1,Map<String,String> m2) {
		if(m1.size()!=m2.size()) {
			return false;
		}
		for(Map.Entry<String, String> entry : m1.entrySet()) {
			if(m2.containsKey(entry.getKey())) {
				if(!entry.getValue().equals(m2.get(entry.getKey()))) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	public double getParam(String key) {
		return parameters.get(key);
	}
	
	public Map<String,String> getCompartment(int x) {
		return compartments.get(x);
	}
	
	
	public TransitionRateMatrix getTransitions() {
		//a modifier pour la composition a plusieurs concerns
		return concerns.get(0).getTransitionRateMatrix();
	}
	
	//a modifier pour la composition a plusieurs concerns
	public void initCompartments(){
		firstCompartment();
		for(int i=1;i<concerns.size();++i) {
			mergeCompartments(concerns.get(i));
		}
	}
	
	public void mergeCompartments(Concern c) {
		List<Map<String,String>> newComparts=new ArrayList<Map<String,String>>();
		String attribute=c.getName();
		for(int i=0;i<compartments.size();++i) {
			for(int j=0;j<c.getNbCompartments();++j) {
				newComparts.add(new HashMap<String,String>());
				newComparts.get(newComparts.size()-1).putAll(compartments.get(i));
				newComparts.get(newComparts.size()-1).put(attribute, c.getCompartment(j));
			}
		}
		compartments=newComparts;
		putCompartsInParams();
	}
	
	public void firstCompartment() {
		String attribute=concerns.get(0).getName();
		for(int i=0;i<concerns.get(0).getNbCompartments();++i) {
			compartments.add(new HashMap<String,String>());
			compartments.get(i).put(attribute, concerns.get(0).getCompartment(i));
		}
	}

	public double getN() {
		double n=0.;
		for(int i=0;i<getNbCompartments();++i) {
			n+=getParam(mapToStringName(compartments.get(i)));
		}
		return n;
	}
	
	public double[] getPopulation() {
		double[] population=new double[getNbCompartments()];
		double n=getN();
		assert n>0.;
		for(int i=0;i<getNbCompartments();++i) {
			population[i]=getParam(mapToStringName(compartments.get(i)));
		}
		return population;
	}
	
	public double[] getProportions() {
		double[] proportion=new double[getNbCompartments()];
		double n=getN();
		assert n>0.;
		for(int i=0;i<getNbCompartments();++i) {
			proportion[i]=getParam(mapToStringName(compartments.get(i)))/n;
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
