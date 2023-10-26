package jKendrick.sans;

import java.util.function.Function;

public class LocalEventDescriptor {
    private String fromState;
    private String toState;
    private Function<int[], Double> rateFunction;

    public LocalEventDescriptor(String fromState, String toState, Function<int[], Double> rateFunction) {
        this.fromState = fromState;
        this.toState = toState;
        this.rateFunction = rateFunction;
    }

    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }

    public double getRate(int[] currentStateCounts) {
        return rateFunction.apply(currentStateCounts);
    }
}
