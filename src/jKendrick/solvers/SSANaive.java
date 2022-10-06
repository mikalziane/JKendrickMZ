package jKendrick.solvers;

import java.util.ArrayList;
import java.util.List;

import jKendrick.IRate;



public class SSANaive {
	private List<IRate> rates;
	private int nbEvents;
	public SSANaive (int nbEvents) {
		if (nbEvents <= 0)
			throw new IllegalArgumentException("At leat one event is expected");
		this.nbEvents = nbEvents;
		this.rates = new ArrayList<>();
	}
	public int getNbRates() { return rates.size();	}
	public int getNbEvents() { return nbEvents; }
	public void addFrate(IRate fr) {
		if (getNbRates() == getNbEvents())
			throw new IllegalStateException(
				"There are already as many rates as there are events.");
		rates.add(fr);
	}
	public IRate get(int i) {
		if (i <0 || i >= getNbRates())
			throw new IllegalArgumentException("invalide rate number : "+i);
		return rates.get(i);
	}
	public int getRateSum() {
		
	}
}
