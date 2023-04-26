import java.util.*;
import java.io.*;
import java.text.DecimalFormat;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		List<Double> queueTimess = new ArrayList<>();
		List<Double> endTimes = new ArrayList<>();

		int amountOfDays = 1000;
		int amountOfCustomer = 0; 

		for(int i = 0; i < amountOfDays; i++){
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning


			insertEvent(ARRIVAL, 0);
			
			// The main simulation loop
			do {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				if(time > (17-9)*60 ){
					if(actState.numberInQueue == 0){
						endTimes.add(time);
						time = 0;
						break;
					}
				}
			}while(true);

			amountOfCustomer = amountOfCustomer + actState.noOfServed;
			queueTimess.addAll(actState.queueTimes);
		}



		double timeAverage = averageDouble(queueTimess);
		double endTimeAverage = averageDouble(endTimes);

		double standardDeviation = standardDev(queueTimess);
		System.out.println(timeAverage);
		double low = timeAverage - 1.96*standardDeviation;
		double high = timeAverage + 1.96*standardDeviation;
		System.out.println("Average time of a prescription fill: (95% confidence interval) (Low) " + low + " - (High) " + high);
		
		double standardDevationEndTime = standardDev(endTimes);
		System.out.println(endTimeAverage);
		double lowEndTime = endTimeAverage - 1.96*standardDevationEndTime;
		double highEndTime = endTimeAverage + 1.96*standardDevationEndTime;

		System.out.println("Average end time of day: (95% confidence interval) (Low) " +  (lowEndTime) + " - (High) " + (highEndTime));


	}
	public static Double standardDev(List<Double> values) {
		double sum = 0;
		for(double i : values){
			sum = sum + i;
		}
		int size = values.size();
		double average = sum/size;

		double variance = 0.0;
		for(double num : values){
			variance = variance + (Math.pow(num-average,2));
		}
		return Math.sqrt(variance/size);
	}

	public static Double averageDouble(List<Double> values){
		double sum = 0;
		for(double i : values){
			sum = sum + i;
		}
		
		return sum/values.size();
	}
}