package tests.jKendrick.simulation;



import java.util.ArrayList;
import java.util.List;

import jKendrick.Visualization;
import jKendrick.rates.DivRate;
import jKendrick.rates.MulRate;
import jKendrick.rates.Rate;
import jKendrick.rates.SumRate;
import jKendrick.simulation.Concern;
import jKendrick.simulation.IRates;
import jKendrick.simulation.Model;
import jKendrick.simulation.Scenario;
import jKendrick.simulation.Simulation;
import jKendrick.solvers.Gillespie;
import jKendrick.solvers.RK4Solver;
import jKendrick.solvers.TauLeap;


public class MainSIS {
	public static void main(String[] args) {
		Concern SI=new Concern("status","S I","beta gamma");
		
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
		Gillespie g=new Gillespie(SISModel);
		Visualization v=new Visualization();
		String title="SIS";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
		Simulation gillespieSim=new Simulation(g, v, title);
		
		tauLeapSim.simulate();
		rk4Sim.simulate();
		gillespieSim.simulate();
		
		
		
	}

	

	
}

