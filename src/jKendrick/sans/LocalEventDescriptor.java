package jKendrick.sans;

public class LocalEventDescriptor {
    private String fromState;
    private String toState;
    private String rateName;
    private double rate;

    public LocalEventDescriptor(String fromState, String toState, String rateName, double rate) {
        this.fromState = fromState;
        this.toState = toState;
        this.rateName = rateName;
        this.rate = rate;
    }

    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }

    public String getRateName() {
        return rateName;
    }

    public double getRate() {
        return rate;
    }
}