package jKendrick;

import jKendrick.concerns.Concern;

public class MainSEIRS {

	public static void main(String[] args) {
		Concern SEIRS=new Concern("S E I R", "lambda sigma gamma nu");
		SEIRS.setTransitionRate("S", "E", "lambda");
		SEIRS.setTransitionRate("E", "I", "sigma");
		SEIRS.setTransitionRate("I", "R", "gamma");
		SEIRS.setTransitionRate("R", "S", "nu");
		
		System.out.println(SEIRS);
		
		Concern Species=new Concern("human bird", "");
		System.out.println(Species);
		
		Concern Country=new Concern("Belgium France Italy Spain Portugal", "true");
		Country.setTransitionRate("Belgium", "France", "true");
		Country.setTransitionRate("France", "Belgium", "true");
		Country.setTransitionRate("France", "Italy", "true");
		Country.setTransitionRate("France", "Spain", "true");
		Country.setTransitionRate("Italy", "France", "true");
		Country.setTransitionRate("Spain", "France", "true");
		Country.setTransitionRate("Spain", "Portugal", "true");
		Country.setTransitionRate("Portugal", "Spain", "true");
		System.out.println(Country);
		

	}

}
