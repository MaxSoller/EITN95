import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;
	Random slump = new Random(); // This is just a random number generator
	
	private int N;
	private int x;
	private double lambda;
	private int T;
	SimpleFileWriter W;
	
	public State(int N, int x, double lambda, int T, String fileName) {
		this.N = N;
		this.x = x;
		this.lambda = lambda;
		this.T = T;
		this.W = new SimpleFileWriter(fileName, false);
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
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberInQueue < N)
			insertEvent(READY, time + x);
		numberInQueue++;
		insertEvent(ARRIVAL, time + poissonDist(lambda));
	}
	
	private void ready(){
		numberInQueue--;
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		W.println(numberInQueue + ", " + noMeasurements);
		insertEvent(MEASURE, time + T);
	}

	private double poissonDist(double mean) {
		double u = slump.nextDouble();
		return -Math.log(1 - u) / mean;
	}
}