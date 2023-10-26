package jKendrick.sans;

import java.util.Arrays;

import jKendrick.core.ISolver;

public class Gillespie implements ISolver {
	  private StochasticAutomaton automaton;
	  private double[][][] results;
	  private double[][] medianPath;
	  private String[] labels;
	  private double end;
	  private double step;
	  private int steps;

	  public Gillespie(StochasticAutomaton automaton, int nbSteps, double stepSize) {
	      this.automaton = automaton;
	      this.labels = automaton.getStates().toArray(new String[0]);
	      this.steps = nbSteps;
	      this.step = stepSize;
	      this.end = nbSteps * stepSize;  // End time is the number of steps multiplied by the size of each step
	  }


    @Override
    public void solve() {
        // Initialize variables
        int numStates =  automaton.getStates().size();
        this.results = new double[1][steps][numStates];
        this.medianPath = new double[steps][numStates];

        // Initialize the state counts for t=0
        int[] currentStateCounts = automaton.getInitialStateCounts();
        for (int j = 0; j < numStates; j++) {
            results[0][0][j] = currentStateCounts[j];
            medianPath[0][j] = currentStateCounts[j];
        }

        int currentStep = 1;
        while (currentStep < steps) {
            double totalRate = 0.0;
            LocalEventDescriptor selectedEvent = null;

            // Calculate total rate
            for (LocalEventDescriptor event : automaton.getEventDescriptors()) {
                double rate = event.getRate(currentStateCounts);
                totalRate += rate;
            }

            // Select event based on rates
            double randomValue = Math.random() * totalRate;
            double sumRates = 0.0;
            for (LocalEventDescriptor event : automaton.getEventDescriptors()) {
                double rate = event.getRate(currentStateCounts);
                sumRates += rate;
                if (randomValue <= sumRates) {
                    selectedEvent = event;
                    break;
                }
            }

            // Update state counts based on selected event
            if (selectedEvent != null) {
                int fromIndex = automaton.getStates().indexOf(selectedEvent.getFromState());
                int toIndex = automaton.getStates().indexOf(selectedEvent.getToState());
                currentStateCounts[fromIndex]--;
                currentStateCounts[toIndex]++;
            }

            // Record the state counts
            for (int j = 0; j < numStates; j++) {
                results[0][currentStep][j] = currentStateCounts[j];
                medianPath[currentStep][j] = currentStateCounts[j];  // Assuming one run, median is the run itself
            }
  
            System.out.println("Debug: Current state counts of the automaton are: " + Arrays.toString(currentStateCounts));

            // Debug: Print current simulation step
            System.out.println("Debug: Current simulation step is: " + currentStep);

            

  
            currentStep++;
        }
    }

    @Override
    public double[][][] getResult() {
        return results;
    }

    @Override
    public double[][] getMedianPath() {
        return medianPath;
    }

    @Override
    public String[] getLabels() {
        return labels;
    }

    @Override
    public double getEnd() {
        return end;
    }

    @Override
    public double getStep() {
        return step;
    }

    @Override
    public double[] getTimes(int cycle) {
        return new double[steps];  // Time not used in Gillespie, but for interface compatibility
    }

    @Override
    public double[] getMedianTimes() {
        return new double[steps];  // Time not used in Gillespie, but for interface compatibility
    }

    @Override
    public int getNbSteps() {
        return steps;
    }
}
