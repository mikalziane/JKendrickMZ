package jKendrick.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitionRateMatrix {
	private HashMap<XY,IRates> rates;
	private ArrayList<String> compartments;
	
	public TransitionRateMatrix(ArrayList<String> compartments) {
		rates=new HashMap<XY,IRates>();
		this.compartments=compartments;
	}
	
	public void setRate(String x, String y, IRates rate) {
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
				rate = -rate;
			}
		}
		else if (rates.containsKey(xy)) {
			rate=rates.get(xy).getRate(s);
		}
		return rate;
	}
	
	public String[][] getPossibleEvents(){
		String[][] events=new String[rates.size()][2];
		int i=0;
		for(Map.Entry<XY, IRates> entry : rates.entrySet()) {
			events[i][0]=entry.getKey().getX();
			events[i][1]=entry.getKey().getY();
			++i;
		}
		return events;
	}
	
	
	
	
	private static class XY{
		private String x;
		private String y;
		public XY(String x, String y) {
			this.x=x;
			this.y=y;
		}
		public String getX() {
			return x;
		}
		public String getY() {
			return y;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof XY))
				return false;
			else {
				XY xy = (XY) o;
				return (x.equals(xy.getX()) && y.equals(xy.getY()));
			}
		}
		
		public int hashCode() { 
			return x.charAt(0) ^ y.charAt(0); 
		}
	
	}

}
