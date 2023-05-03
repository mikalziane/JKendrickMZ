package jKendrick.events;

public abstract class Event implements IEvent{
	private double rate;
	public Event(double rate) {
		this.rate=rate;
	}
	
	public double getRate() {
		return rate;
	}
	
	public int indexOf(String[] compartments, String x) {
		int index=-1;
		for(int i=0;i<compartments.length;++i) {
			if(compartments[i].equals(x)) {
				index=i;
			}
		}
		return index;
	}
}
