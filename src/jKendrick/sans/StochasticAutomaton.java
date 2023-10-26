package jKendrick.sans;

import java.util.Arrays;
import java.util.List;


public class StochasticAutomaton {
    private List<String> states;
    private List<LocalEventDescriptor> eventDescriptors;
    private int[] currentStateCounts;  // Instance variable to hold the current state counts

    public StochasticAutomaton(List<String> states, List<LocalEventDescriptor> eventDescriptors, int[] initialStateCounts) {
        this.states = states;
        this.eventDescriptors = eventDescriptors;
        this.currentStateCounts = Arrays.copyOf(initialStateCounts, states.size());  // Initialize with initial state counts
    }
    public double[][] simulate(int steps) {
        int numStates = states.size();
        double[][] counts = new double[numStates][steps];

        // Initialize the state counts for t=0
        for (int j = 0; j < numStates; j++) {
            counts[j][0] = currentStateCounts[j];
        }

        double currentTime = 0.0;
        int currentStep = 1;

        while (currentStep < steps) {
            double totalRate = 0.0;
            LocalEventDescriptor selectedEvent = null;

            // Calculate total rate
            for (LocalEventDescriptor event : eventDescriptors) {
                double rate = event.getRate(currentStateCounts);
                totalRate += rate;
            }

            // Time until next event
            double timeToNextEvent = -Math.log(Math.random()) / totalRate;
            currentTime += timeToNextEvent;

            // Select event based on rates
            double randomValue = Math.random() * totalRate;
            double sumRates = 0.0;
            for (LocalEventDescriptor event : eventDescriptors) {
                double rate = event.getRate(currentStateCounts);
                sumRates += rate;
                if (randomValue <= sumRates) {
                    selectedEvent = event;
                    break;
                }
            }

            // Update state counts based on selected event
            if (selectedEvent != null) {
                int fromIndex = states.indexOf(selectedEvent.getFromState());
                int toIndex = states.indexOf(selectedEvent.getToState());
                currentStateCounts[fromIndex]--;
                currentStateCounts[toIndex]++;
            }

            // Record the state counts
            for (int j = 0; j < numStates; j++) {
                counts[j][currentStep] = currentStateCounts[j];
            }

            currentStep++;
        }

        return counts;
    }
	public List<String> getStates() {
		return states;
	}
	public List<LocalEventDescriptor> getEventDescriptors() {
        return eventDescriptors;
    }
	public int[] getInitialStateCounts() {
		return currentStateCounts.clone();
	}
}
