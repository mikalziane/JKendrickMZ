package jKendrick.IHM;

import jKendrick.core.Concern;
import jKendrick.core.IRates;
import jKendrick.core.Model;

import jKendrick.rates.DivRate;
import jKendrick.rates.MulRate;
import jKendrick.rates.Rate;
import jKendrick.rates.SumRate;

import java.util.ArrayList;
import java.util.List;

import jKendrick.simulation.Scenario;
import jKendrick.simulation.Simulation;
import jKendrick.solvers.Gillespie;
import jKendrick.solvers.RK4Solver;
import jKendrick.solvers.TauLeap;
public class MainSIR {
	public static void main(String[] args) {
		Concern SIR=new Concern("status","S I R","beta gamma");
		IRates beta=new Rate("beta");
		IRates S=new Rate("status:S_");
		IRates I=new Rate("status:I_");
		IRates R=new Rate("status:R_");
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
		SIRscenario.setParameter("status:S_", 999.);
		SIRscenario.setParameter("status:I_", 1.);
		SIRscenario.setParameter("status:R_", 0.);
		
		SIRscenario.setParameter("beta", 1.4247);
		SIRscenario.setParameter("gamma", 0.14286);
		
		int nbCycle=100;
		double end=70.;
		double step=1;
		
		Model SIRModel=new Model(SIRscenario,step, end, nbCycle);
		TauLeap tl=new TauLeap(SIRModel);
		RK4Solver rksolver=new RK4Solver(SIRModel);
		Gillespie g=new Gillespie(SIRModel);
		Visualization v=new Visualization();
		String title="SIR";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
		Simulation gillespieSim=new Simulation(g, v, title);
		
		tauLeapSim.simulate();
		rk4Sim.simulate();
		gillespieSim.simulate();
	}

}
