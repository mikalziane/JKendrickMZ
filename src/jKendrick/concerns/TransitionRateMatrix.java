package jKendrick.concerns;

import java.util.ArrayList;
import java.util.HashMap;

import jKendrick.scenario.Scenario;

public class TransitionRateMatrix {
	private HashMap<XY,Rate> rates;
	private ArrayList<String> compartments;
	
	public TransitionRateMatrix(ArrayList<String> compartments) {
		rates=new HashMap<XY,Rate>();
		this.compartments=compartments;
	}
	
	public void setRate(String x, String y, Rate rate) {
		assert (compartments.contains(x) && compartments.contains(y));
		XY xy=new XY(x,y);
		rates.put(xy,rate);
	}
	
	public void addCompartment(String c) {
		if(!compartments.contains(c)) {
			compartments.add(c);
		}
	}
	
	public double getRate(String x, String y, Scenario s) {
		assert (compartments.contains(x) && compartments.contains(y));
		double rate=0.;
		XY xy=new XY(x,y);
		if(x.equals(y)) {
			for(int i=0;i<compartments.size();++i) {
				if(i!=compartments.indexOf(x)) {
					rate+=getRate(x,compartments.get(i),s);
				}
			}
		}
		else if (rates.containsKey(xy)) {
			rate=rates.get(xy).getRate(s);
		}
		return rate;
	}
	
	
	private static class XY{
		private String x;
		private String y;
		public XY(String x, String y) {
			this.x=x;
			this.y=y;
		}
	
	}

}
