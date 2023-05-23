package jKendrick;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jKendrick.events.EndOfImmunity;
import jKendrick.events.IEvent;
import jKendrick.events.Infection;
import jKendrick.events.Recovery;
import jKendrick.solvers.Gillespie;

public class MainSiRGillespie {

	public static void main(String[] args) {
		Map<String, Integer> nbIndiv=new HashMap<>();
		nbIndiv.put("S", 99);
		nbIndiv.put("I", 1);
		nbIndiv.put("R", 0);
		int nbCycle=100;
		int nbStep=500;
		IEvent Infect=new Infection(6.0);
		IEvent Recov=new Recovery(0.6);
		IEvent EndImmu=new EndOfImmunity(0.1);
		IEvent[] events= {Infect,Recov,EndImmu}; 
		Gillespie g=new Gillespie(nbCycle, nbStep, nbIndiv, events);
		g.solve();
		double[][][] result=g.getResult();
		double[][] average=g.getAverage();
		
		for(int a=0;a<result.length;++a) {
			System.out.println("Cycle "+a);
			for(int b=0;b<result[0].length;++b) {
				for(int c=0;c<result[0][0].length;++c) {
					System.out.print(result[a][b][c]+" - ");
				}
				System.out.println(" ");
			}
		}
		
		//display of averages values
		System.out.println("Moyenne:");
		for(int i=0;i<average.length;++i) {
			for(int j=0;j<average[0].length;++j) {
				System.out.print(average[i][j]+" - ");
			}
			System.out.println(" ");
		}
		double avStep=g.getAverageStep();
		
		double[][] medianPath=g.getMedianPath();
		
		double[][] values=g.getValues();
		double last=avStep*nbStep-0.0000001;
		String[] labels=nbIndiv.keySet().toArray((new String[nbIndiv.size()]));
		Visualization v=new Visualization();
		/*try {
		v.xchartExample(values, avStep, last, labels, "Test SIR", "population", "temps");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/*try {
			v.stochasticChart(result, average, labels, "Test SIR", "population", "temps");
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
		try {
			v.stochasticChart(result, medianPath, labels, "Test SIR", "temps","population");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}

}
