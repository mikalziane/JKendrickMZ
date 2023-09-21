package jKendrick.core;



import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;



public class Concern {
	private ArrayList<String> compartmentNames;
	private Map<String, Double> parameters;
	private TransitionRateMatrix transitionRates;
	private String name;
	
	public Concern(String name, String compartments, String parametersNames) {
		assert compartments.length()>0;
		this.name=name;
		compartmentNames=new ArrayList<String>();
		String[] splitCompart = compartments.split(" ");
		for(int i=0;i<splitCompart.length;++i) {
			compartmentNames.add(splitCompart[i]);
		}
		String[] param=parametersNames.split(" ");
		parameters=new HashMap<>();
		for(int i=0;i<param.length;++i) {
			parameters.put(param[i], 0.);
		}
		transitionRates=new TransitionRateMatrix(mapOfComparts());
	}
	
	public List<Map<String,String>> mapOfComparts(){
		List<Map<String,String>> comparts=new ArrayList<Map<String,String>>();
		for(int i=0;i<getNbCompartments();++i) {
			comparts.add(new HashMap<String,String>());
			comparts.get(i).put(name, getCompartment(i));
		}
		return comparts;
	}
	
	public String getName() {
		return name;
	}
	
	public void setParameter(String param,double value) {
		parameters.put(param, value);
	}
	
	public TransitionRateMatrix getTransitionRateMatrix() {
		return transitionRates;
	}
	
	public double getParameter(String param) {
		return parameters.get(param);
	}
	
	public Map<String, Double> getParameters() {
		return parameters;
	}
	
	public int indexOfCompartment(String compartment) {
		return compartmentNames.indexOf(compartment);
	}
	
	public String getCompartment(int i) {
		return compartmentNames.get(i);
	}
	
	public void setTransitionRate(String from,String to,IRates rate) {
		assert compartmentNames.contains(from) && compartmentNames.contains(to);
		Map<String,String> fromName=new HashMap<>();
		fromName.put(name, from);
		Map<String,String> toName=new HashMap<>();
		toName.put(name, to);
		transitionRates.setRate(fromName, toName, rate);
	}
	
	public ArrayList<String> getCompartments() {
		ArrayList<String> list = new ArrayList<String>();
	     for(String c:compartmentNames) {
	        list.add(c);
	     }
	     return list;
	}
	
	public int getNbCompartments() {
		return compartmentNames.size();
	}
	
	/*@Override
	public String toString() {
		String spacer="	";
		StringBuilder s=new StringBuilder();
		s.append(spacer);
		for(int i=0; i<compartmentNames.size();++i) {
			s.append(compartmentNames.get(i));
			s.append(spacer);
		}
		s.append("\n");
		for(int i=0;i<transitionRates.;++i) {
			s.append(compartmentNames[i]);
			s.append(spacer);
			for(int j=0;j<transitionRates[0].length;++j) {
				s.append(transitionRates[i][j]);
				s.append(spacer);
			}
			s.append("\n");
		}
		return s.toString();
	}*/
	

}
