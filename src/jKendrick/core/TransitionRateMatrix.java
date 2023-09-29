package jKendrick.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jKendrick.simulation.Scenario;

public class TransitionRateMatrix {
	private HashMap<XY,IRates> rates;
	private List<Map<String,String>> compartments;
	
	public TransitionRateMatrix(List<Map<String,String>> compartments) {
		rates=new HashMap<XY,IRates>();
		this.compartments=compartments;
	}
	
	public void setRate(Map<String,String> x, Map<String,String> y, IRates rate) {
		assert (compartments.contains(x) && compartments.contains(y));
		XY xy=new XY(x,y);
		rates.put(xy,rate);
	}
	
	public void addCompartment(Map<String,String> c) {
		if(!compartments.contains(c)) {
			compartments.add(c);
		}
	}
	
	public double getRate(Map<String,String> x, Map<String,String> y, Scenario s) {
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
	
	public Map<String,String>[][] getPossibleEvents(){
		@SuppressWarnings("unchecked")
		Map<String,String>[][] events=new HashMap[rates.size()][2];
		int i=0;
		for(Map.Entry<XY, IRates> entry : rates.entrySet()) {
			events[i][0]=entry.getKey().getX();
			events[i][1]=entry.getKey().getY();
			++i;
		}
		return events;
	}
	
	public void printCompart() {
		for(int i=0; i<compartments.size();++i) {
			Map<String,String> m=compartments.get(i);
			for(Map.Entry<String, String> entry : m.entrySet()) {
				System.out.println(entry.getKey()+" : "+entry.getValue());
			}
		}
		
	}
	
	
	
	
	private static class XY{
		private Map<String,String> x;
		private Map<String,String> y;
		public XY(Map<String,String> x, Map<String,String> y) {
			assert x.size()==y.size();
			this.x=x;
			this.y=y;
		}
		public Map<String,String> getX() {
			return x;
		}
		public Map<String,String> getY() {
			return y;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof XY))
				return false;
			else {
				XY xy = (XY) o;
				return (equivalent(xy.getX(),x) && equivalent(xy.getY(),y));
			}
		}
		
		public boolean equivalent(Map<String,String> m1,Map<String,String> m2) {
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
		
		public int hashCode() {
			int n=0;
			for(Map.Entry<String, String> entry : x.entrySet()) {
				int xvalue=entry.getValue().hashCode();
				n+=(xvalue);
			}
			return n; 
		}
	
	}

}
