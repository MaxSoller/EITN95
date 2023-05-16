import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		Random slump = new Random();
 // The state that shoud be used
    	// Some events must be put in the event list at the beginning
		Event actEvent;
		State actState;
		double boatPrice = 2000000.0;
        int amountOfSimulations = 0;
		double confidence = Integer.MAX_VALUE;
		LinkedList<Double> results = new LinkedList<>();
		LinkedList<Double> averages = new LinkedList<>();
        // The main simulation loop
		while(confidence > 2){
			actState = new State();
			insertEvent(MONTH, 0);  
			insertEvent(CRASH, slump.nextDouble(12*4*2));

			while (actState.money < boatPrice){
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			amountOfSimulations++;
			results.addLast(time);
			confidence = averageDouble(results)/Math.sqrt(amountOfSimulations);
			time = 0;
			}
		System.out.println("Done");
		System.out.println("Average months to reach boat: " + averageDouble(results) + " \u00B1 " + confidence);
		System.out.println("Amount of months ");
		System.out.println(amountOfSimulations);
		for(double i : results){
			
			//System.out.println(i);
		}

		
    }
	public static Double averageDouble(List<Double> values){
		double sum = 0;
		for(double i : values){
			sum = sum + i;
		}
		return sum/values.size();
	}
}