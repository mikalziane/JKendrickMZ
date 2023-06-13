package jKendrick;

import jKendrick.scenario.Scenario;

public interface IRate {
	double eval(double t, double[] model);
	double getRate(Scenario s);
}