package jKendrick.scenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jKendrick.concerns.Concern;

public class Scenario {
	private List<Concern> concerns;
	private Map<String,Double> parameters;
	
	public Scenario(List<Concern> concerns) {
		this.concerns=concerns;
		parameters=new HashMap<>();
		for (int i = 0; i < concerns.size(); ++i) {
			parameters.putAll(concerns.get(i).getParameters());
		}
	}
	
	public double getParam(String key) {
		return parameters.get(key);
	}

}
