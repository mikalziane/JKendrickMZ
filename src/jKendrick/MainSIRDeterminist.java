package jKendrick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jKendrick.concerns.Concern;
import jKendrick.concerns.DivRate;
import jKendrick.concerns.IRates;
import jKendrick.concerns.MulRate;
import jKendrick.concerns.Rate;
import jKendrick.concerns.SumRate;
import jKendrick.scenario.Scenario;
import jKendrick.solvers.RK4Solver;

public class MainSIRDeterminist {
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
		
		double step=1;
		double last=70.;
		
		RK4Solver solver=new RK4Solver(step);
		
		double[][] result=solver.solve(step, last, SIRscenario);
		
		
		Visualization viz = new Visualization ();
		
		try {
			viz.deterministicChart(result, step, last, SIRscenario.getCompartments().toArray(new String[SIRscenario.getNbCompartments()]), "SIR Deterministic", "time", "population");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
