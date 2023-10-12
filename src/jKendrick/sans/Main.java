package jKendrick.sans;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // SIR Automaton
        List<String> sirStates = Arrays.asList("S", "I", "R");
        List<LocalEventDescriptor> sirEvents = Arrays.asList(
            new LocalEventDescriptor("S", "I", "beta", 0.3),
            new LocalEventDescriptor("I", "R", "gamma", 0.1)
        );
        StochasticAutomaton sirAutomaton = new StochasticAutomaton(sirStates, sirEvents, "I");

        // North-South Automaton
        List<String> nsStates = Arrays.asList("North", "South");
        List<LocalEventDescriptor> nsEvents = Arrays.asList(
            new LocalEventDescriptor("North", "South", "move_south", 0.5),
            new LocalEventDescriptor("South", "North", "move_north", 0.5)
        );
        StochasticAutomaton nsAutomaton = new StochasticAutomaton(nsStates, nsEvents, "North");

        // Tensor Sum
        List<StochasticAutomaton> automata = Arrays.asList(sirAutomaton, nsAutomaton);
        StochasticAutomaton combinedAutomaton = StochasticAutomaton.tensorSum(automata);

        // Print the resulting SAN states
        System.out.println("Combined SAN States:");
        for (String state : combinedAutomaton.getStates()) {
            System.out.println(state);
        }
    }
}