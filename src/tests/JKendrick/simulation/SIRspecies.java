package tests.JKendrick.simulation;

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

public class SIRspecies {

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
		
		Concern species=new Concern("species","human bird","");
		
		List<Concern> concerns=new ArrayList<Concern>();
		concerns.add(SIR);
		concerns.add(species);
		
		Scenario SIRscenario=new Scenario(concerns);
		SIRscenario.setPopulation("status:S_species:human_", 999.);
		SIRscenario.setPopulation("status:I_species:human_", 0.);
		SIRscenario.setPopulation("status:R_species:human_", 0.);
		SIRscenario.setPopulation("status:S_species:bird_", 999.);
		SIRscenario.setPopulation("status:I_species:bird_", 1.);
		SIRscenario.setPopulation("status:R_species:bird_", 0.);
		
		
		SIRscenario.setParameter("beta", 1.4247);
		SIRscenario.setParameter("gamma", 0.14286);
		
		SIRscenario.printStringNames();
		
		

	

	}

}
