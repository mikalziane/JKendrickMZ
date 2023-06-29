package jKendrick;




import java.util.ArrayList;
import java.util.List;


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
		Gillespie g=new Gillespie(SEIRModel);
		Visualization v=new Visualization();
		String title="SEIR";
		
		Simulation tauLeapSim=new Simulation(tl, v, title);
		Simulation rk4Sim=new Simulation(rksolver, v, title);
		Simulation gillespieSim=new Simulation(g, v, title);
		
		tauLeapSim.simulate();
		rk4Sim.simulate();
		gillespieSim.simulate();
		
		
		
		
		
	}
	
	// ajouter démographie : mu=0.0000391;
		
	
}
