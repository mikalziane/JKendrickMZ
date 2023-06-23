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
import jKendrick.models.SEIR;
import jKendrick.scenario.Model;
import jKendrick.scenario.Scenario;
import jKendrick.scenario.Simulation;
import jKendrick.solvers.RK4Solver;
import jKendrick.solvers.TauLeap;


public class MainSEIR {
	public static void main(String[] args) {
		Concern SEIR=new Concern("S E I R ","beta gamma sigma");
		IRates S=new Rate("S");
		IRates E=new Rate("E");
		IRates I=new Rate("I");
		IRates R=new Rate("R");
		IRates N=new SumRate(new SumRate(new SumRate(R,I),E),S);
		IRates beta=new Rate("beta");
		IRates gamma=new Rate("gamma");
		IRates sigma=new Rate("sigma");
		IRates lambda=new DivRate(new MulRate(beta, new MulRate(I,S)), N) ;
		IRates sigmaE=new MulRate(sigma, E);
		IRates gammaI=new MulRate(gamma, I);
		
		SEIR.setTransitionRate("S", "E", lambda);
		SEIR.setTransitionRate("E", "I", sigmaE);
		SEIR.setTransitionRate("I", "R", gammaI);
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(SEIR);
		
		Scenario SEIRscenario=new Scenario(concerns);
		SEIRscenario.setParameter("S", 1000.);
		SEIRscenario.setParameter("E", 1.);
		SEIRscenario.setParameter("I", 1.);
		SEIRscenario.setParameter("R", 898.);
		
		SEIRscenario.setParameter("beta", 1.4247);
		SEIRscenario.setParameter("gamma", 0.14286);
		SEIRscenario.setParameter("sigma", 0.07143);
		
		
		double step = 1;
		double last = 70.;
		int nbCycles=100;
		
		Model SEIRModel=new Model(SEIRscenario, step, last, nbCycles);
		
		TauLeap tl=new TauLeap(SEIRModel);
		RK4Solver rksolver=new RK4Solver(SEIRModel);
		Visualization v=new Visualization();
		String title="SEIR";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
		System.out.println("ready");
		
		rk4Sim.simulate();
		tauLeapSim.simulate();
		
		
		System.out.println("done");
		
		
	}
	
	// ajouter démographie : mu=0.0000391;
		
	
}
