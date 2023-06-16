package jKendrick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import jKendrick.concerns.Concern;
import jKendrick.scenario.Scenario;
import jKendrick.concerns.DivRate;

import jKendrick.solvers.TauLeap;
import jKendrick.concerns.IRates;
import jKendrick.concerns.MulRate;
import jKendrick.concerns.Rate;
import jKendrick.concerns.SumRate;
public class MainSIRTauLeap {

	public static void main(String[] args) {
		Concern SIR=new Concern("S I R","beta gamma");
		IRates beta=new Rate("beta");
		IRates S=new Rate("S");
		IRates I=new Rate("I");
		IRates R=new Rate("R");
		IRates addSI=new SumRate(S, I);
		IRates N=new SumRate(addSI, R);
		IRates betaI=new MulRate(beta, I);
		IRates betaIS=new MulRate(betaI, S);
		IRates lambda=new DivRate(betaIS, N);
		IRates gamma=new Rate("gamma");
		IRates recovery=new MulRate(gamma, I);
		
		SIR.setTransitionRate("S", "I", lambda);
		SIR.setTransitionRate("I", "R", recovery);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(SIR);
		
		Scenario SIRscenario=new Scenario(concerns);
		SIRscenario.setParameter("S", 999.);
		SIRscenario.setParameter("I", 1.);
		SIRscenario.setParameter("R", 0.);
		
		SIRscenario.setParameter("beta", 1.4247);
		SIRscenario.setParameter("gamma", 0.14286);
		
		int nbCycle=100;
		int nbStep=700;
		double step=0.1;
		
		TauLeap tl=new TauLeap(step, nbCycle, nbStep, SIRscenario );
		tl.solve();
		double[][][] result=tl.getResult();
		double[][] medianPath=tl.getMedianPath();
		String[] labels= {"S","I","R"};
		Visualization v=new Visualization();
		try {
			v.stochasticChart(step,result, medianPath, labels, "Test SIR", "temps","population");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

}
