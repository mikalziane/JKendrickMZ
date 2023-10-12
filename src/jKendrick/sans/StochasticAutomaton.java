package jKendrick.sans;

import java.util.ArrayList;
import java.util.List;

public class StochasticAutomaton {
    private List<String> states;
    private List<LocalEventDescriptor> localEvents;
    private String currentState;

    public StochasticAutomaton(List<String> states, List<LocalEventDescriptor> localEvents, String initialState) {
        this.setStates(states);
        this.localEvents = localEvents;
        this.currentState = initialState;
    }

    private double getTotalRate() {
        return localEvents.stream()
            .filter(e -> e.getFromState().equals(currentState))
            .mapToDouble(LocalEventDescriptor::getRate)
            .sum();
    }

    private String selectTransition() {
        double totalRate = getTotalRate();
        double rand = Math.random() * totalRate;
        double sum = 0;

        for (LocalEventDescriptor event : localEvents) {
            if (event.getFromState().equals(currentState)) {
                sum += event.getRate();
                if (sum >= rand) {
                    return event.getToState();
                }
            }
        }

        return null; // Should not reach here if the descriptors are well-defined
    }

    public void simulate() {
        double t = 0;

        while (t < 100) {
            // Calculate total rate
            double totalRate = getTotalRate();

            // Generate time step
            double dt = -Math.log(Math.random()) / totalRate;

            // Select transition
            String nextState = selectTransition();

            // Update state
            currentState = nextState;

            // Update time
            t += dt;
        }
    }

    public static StochasticAutomaton tensorSum(List<StochasticAutomaton> automata) {
        List<String> combinedStates = new ArrayList<>();
        List<LocalEventDescriptor> combinedEvents = new ArrayList<>();

        // Generate combined states
        generateCombinedStates(combinedStates, automata, 0, "");

        // Generate combined events
        for (StochasticAutomaton automaton : automata) {
            for (LocalEventDescriptor event : automaton.localEvents) {
                addCombinedEvents(combinedEvents, automata, event, 0, "", "");
            }
        }

        return new StochasticAutomaton(combinedStates, combinedEvents, "");  // Initial state needs to be set appropriately
    }

    private static void generateCombinedStates(List<String> combinedStates, List<StochasticAutomaton> automata, int index, String prefix) {
        if (index == automata.size()) {
            combinedStates.add(prefix);
            return;
        }

        for (String state : automata.get(index).getStates()) {
            generateCombinedStates(combinedStates, automata, index + 1, prefix + state);
        }
    }

    private static void addCombinedEvents(List<LocalEventDescriptor> combinedEvents, List<StochasticAutomaton> automata, LocalEventDescriptor event, int index, String fromPrefix, String toPrefix) {
        if (index == automata.size()) {
            combinedEvents.add(new LocalEventDescriptor(fromPrefix, toPrefix, event.getRateName(), event.getRate()));
            return;
        }

        for (String state : automata.get(index).getStates()) {
            String newFromPrefix = fromPrefix + (event.getFromState().equals(state) ? event.getFromState() : state);
            String newToPrefix = toPrefix + (event.getToState().equals(state) ? event.getToState() : state);
            addCombinedEvents(combinedEvents, automata, event, index + 1, newFromPrefix, newToPrefix);
        }
    }

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}


}
