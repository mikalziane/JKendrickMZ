package jKendrick.events;

public interface IEvent {
	public double getRate();
	public double[] action(String[] compartments, double[] population);
	
}
