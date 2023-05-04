import java.util.*;
import java.io.*;
import java.text.DecimalFormat;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		List<Double> averageQueueTimess = new ArrayList<>();
		List<Double> endTimes = new ArrayList<>();
		int startingTime = 9*60;
		int endingTime = 17*60;

		int amountOfDays = 10000;
		
		for(int i = 0; i < amountOfDays; i++){
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning


			insertEvent(ARRIVAL, startingTime);
			time = startingTime;
			// The main simulation loop
			do {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;

				actState.treatEvent(actEvent);
				if(time > endingTime ){
					if(actState.numberInQueue == 0){
						endTimes.add(time/60);
						time = startingTime;
						break;
					}
				}
			}while(true);

			averageQueueTimess.add(averageDouble(actState.queueTimes));
		}

		double confidenceQ = averageDouble(averageQueueTimess)/Math.sqrt(amountOfDays);
		double confidenceE = averageDouble(endTimes)/Math.sqrt(amountOfDays);

		printResults(averageDouble(averageQueueTimess), confidenceQ, "queue times");
		printResults(averageDouble(endTimes), confidenceE, "end times");

		


	}
	public static void printResults(double average ,double confidence, String what){
		System.out.println("------------------------------------------");
		System.out.println("The 95% interval for " + what + " is " + average + " \u00B1 " + 1.96*confidence);
	}
	public static Double standardDev(List<Double> values) {
		double average = averageDouble(values);

		double variance = 0.0;
		for(double num : values){
			variance = variance + (Math.pow(num-average,2));
		}
		variance = variance/values.size();
		return Math.sqrt(variance);
	}

	public static Double averageDouble(List<Double> values){
		double sum = 0;
		for(double i : values){
			sum = sum + i;
		}
		return sum/values.size();
	}
}