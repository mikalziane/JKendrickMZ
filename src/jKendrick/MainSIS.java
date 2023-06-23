package jKendrick;



import java.util.ArrayList;
import java.util.List;

import jKendrick.concerns.Concern;
import jKendrick.concerns.DivRate;
import jKendrick.concerns.IRates;
import jKendrick.concerns.MulRate;
import jKendrick.concerns.Rate;
import jKendrick.concerns.SumRate;

import jKendrick.scenario.Model;
import jKendrick.scenario.Scenario;
import jKendrick.scenario.Simulation;
import jKendrick.solvers.RK4Solver;
import jKendrick.solvers.TauLeap;


public class MainSIS {
	public static void main(String[] args) {
		Concern SI=new Concern("S I","beta gamma");
		
		IRates S=new Rate("S");
		IRates I=new Rate("I");
		IRates N=new SumRate(I,S);
		IRates beta=new Rate("beta");
		IRates gamma=new Rate("gamma");
		IRates lambda=new DivRate(new MulRate(beta, new MulRate(I,S)), N) ;
		IRates gammaI=new MulRate(gamma, I);
		
		SI.setTransitionRate("S", "I", lambda);
		SI.setTransitionRate("I", "S", gammaI);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(SI);
		
		Scenario SISscenario=new Scenario(concerns);
		SISscenario.setParameter("S", 999.);
		SISscenario.setParameter("I", 1.);
		
		SISscenario.setParameter("beta", 1.4247);
		SISscenario.setParameter("gamma", 0.14286);
		
		
		
		double step = 1;
		double last = 70.;
		int nbCycle=100;
		
		Model SISModel=new Model(SISscenario,step, last, nbCycle);
		TauLeap tl=new TauLeap(SISModel);
		RK4Solver rksolver=new RK4Solver(SISModel);
		Visualization v=new Visualization();
		String title="SIS";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
	
		rk4Sim.simulate();
		tauLeapSim.simulate();
		
		
		
	}

	

	
}

