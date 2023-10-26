package jKendrick.sans;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jKendrick.IHM.Visualization;


public class Main {
    public static void main(String[] args) throws IOException {
        // Initial population
        int totalPopulation = 1000;
        int initialInfected = 1;
        int initialSusceptible = 999;
        int initialRecovered = 0;

        // Initial state counts
        int[] initialStateCounts = {initialSusceptible, initialInfected, initialRecovered};

        // Define states and transitions
        List<String> states = Arrays.asList("S", "I", "R");
        List<LocalEventDescriptor> events = Arrays.asList(
            new LocalEventDescriptor("S", "I", stateCounts -> 1.4247 * stateCounts[0] * stateCounts[1] / totalPopulation),
            new LocalEventDescriptor("I", "R", stateCounts -> 0.14286 * stateCounts[1])
        );

        // Create the stochastic automaton
        StochasticAutomaton automaton = new StochasticAutomaton(states, events, initialStateCounts);

        // Create the Gillespie solver and set it up with the automaton
        Gillespie solver = new Gillespie(automaton, 100, 40);
        solver.solve();
        
        Visualization v = new Visualization();
        
        solver.solve();
        v.getChart(solver, "SIR (Gillespie)", "time", "population");
    }


}
