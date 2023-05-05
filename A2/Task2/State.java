import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, noOfServed = 0;
	private double lambda = 15.0; //4 per hour means lambda = 15
	private int finishingTime = 17*60; 
	
	Random slump = new Random(); // This is just a random number generator
	LinkedList<Double> times = new LinkedList<Double>();
	List<Double> queueTimes  = new ArrayList<>();

	public double poisson_dist(double dist) {
		return Math.log(1 - slump.nextDouble()) / (-(1 / dist));
	}
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
				ready();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberInQueue == 0 && time <= finishingTime){
			insertEvent(READY, time + (slump.nextDouble()*10 + 10));
		}
		if(time <= 	finishingTime){
			numberInQueue++;

			double nextTime = time + poisson_dist(lambda);
			insertEvent(ARRIVAL, nextTime);
			times.addLast(time);
		}
	}
	
	private void ready(){
		numberInQueue--;
		noOfServed++;
		double calc = time - times.pop();
		queueTimes.add(calc);
		if (numberInQueue > 0)
			insertEvent(READY, time + (slump.nextDouble()*10 + 10));
	}
	
}