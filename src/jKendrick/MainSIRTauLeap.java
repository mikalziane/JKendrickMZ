package jKendrick;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jKendrick.concerns.Concern;
import jKendrick.events.IEvent;
import jKendrick.events.Infection;
import jKendrick.events.Recovery;
import jKendrick.solvers.TauLeap;

public class MainSIRTauLeap {

	public static void main(String[] args) {
		Concern SIR=new Concern("S I R","lambda gamma");
		SIR.setTransitionRate("S", "I", "lambda");
		SIR.setTransitionRate("I", "R", "gamma");
		
		
		
		Map<String, Integer> nbIndiv=new HashMap<>();
		nbIndiv.put("S", 999);
		nbIndiv.put("I", 1);
		nbIndiv.put("R", 0);
		int nbCycle=100;
		int nbStep=700;
		double step=0.1;
		IEvent Infect=new Infection(1.4247);
		IEvent Recov=new Recovery(0.14286);
		IEvent[] events= {Infect,Recov};
		TauLeap tl=new TauLeap(step, events, nbCycle, nbStep, nbIndiv);
		tl.solve();
		double[][][] result=tl.getResult();
		double[][] medianPath=tl.getMedianPath();
		String[] labels=nbIndiv.keySet().toArray((new String[nbIndiv.size()]));
		Visualization v=new Visualization();
		try {
			v.stochasticChart(step,result, medianPath, labels, "Test SIR", "temps","population");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

}
