package jKendrick.concerns;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;

public class Concern {
	private String[] compartmentNames;
	private Map<String, Double> parameters;
	private String[][] transitionRates;
	
	public Concern(String compartments, String parametersNames) {
		compartmentNames = compartments.split(" ");
		String[] param=parametersNames.split(" ");
		parameters=new HashMap<>();
		for(int i=0;i<param.length;++i) {
			parameters.put(param[i], null);
		}
		transitionRates=new String[compartmentNames.length][compartmentNames.length];
	}
	
	public void setParameter(String param,double value) {
		parameters.put(param, value);
	}
	
	public double getParameter(String param) {
		return parameters.get(param);
	}
	
	public int indexOfCompartment(String compartment) {
		int index=-1;
		for(int i=0;i<compartmentNames.length;++i) {
			if(compartmentNames[i].equals(compartment)) {
				index=i;
			}
		}
		return index;
	}
	
	public void setTransitionRate(String from,String to,String parameter) {
		int fromIndex=indexOfCompartment(from);
		int toIndex=indexOfCompartment(to);
		assert (fromIndex>=0 && toIndex >=0);
		transitionRates[fromIndex][toIndex]=parameter;
	}
	
	@Override
	public String toString() {
		String spacer="	";
		StringBuilder s=new StringBuilder();
		s.append(spacer);
		for(int i=0; i<compartmentNames.length;++i) {
			s.append(compartmentNames[i]);
			s.append(spacer);
		}
		s.append("\n");
		for(int i=0;i<transitionRates.length;++i) {
			s.append(compartmentNames[i]);
			s.append(spacer);
			for(int j=0;j<transitionRates[0].length;++j) {
				s.append(transitionRates[i][j]);
				s.append(spacer);
			}
			s.append("\n");
		}
		return s.toString();
	}
	

}
