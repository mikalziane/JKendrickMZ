package jKendrick;


import jKendrick.scenario.Model;
import java.util.ArrayList;
import java.util.List;


import jKendrick.concerns.Concern;
import jKendrick.scenario.Scenario;
import jKendrick.scenario.Simulation;
import jKendrick.concerns.DivRate;

import jKendrick.solvers.RK4Solver;
import jKendrick.solvers.TauLeap;
import jKendrick.concerns.IRates;
import jKendrick.concerns.MulRate;
import jKendrick.concerns.Rate;
import jKendrick.concerns.SumRate;
public class MainSIR {

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
		double end=70.;
		double step=1;
		
		Model SIRModel=new Model(SIRscenario,step, end, nbCycle);
		TauLeap tl=new TauLeap(SIRModel);
		RK4Solver rksolver=new RK4Solver(SIRModel);
		Visualization v=new Visualization();
		String title="SIR";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
		
		tauLeapSim.simulate();
		rk4Sim.simulate();
		
		

	}

}
