package jKendrick.events;

public interface IEvent {
	public double getRate(String[] compartments, double[] population);
	public double[] action(String[] compartments, double[] population);
	
}
