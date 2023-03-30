import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue1 = 0, accumulated = 0, noMeasurements = 0, nbrOfDropped = 0, nbrOfAccepted = 0, numberInQueue2 = 0;

	Random slump = new Random(); // This is just a random number generator
	private double randExpNumber(double mean){
		return (-mean * Math.log(slump.nextDouble()));
	}
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_FIRST_QUEUE:
				arrivalQueue1();
				break;
			case ARRIVAL_SECOND_QUEUE:
				arrivalQueue2();
				break;
			case READY_FIRST_QUEUE:
				readyQueue1();
				break;
			case READY_SECOND_QUEUE:
				readyQueue2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrivalQueue1(){
		if (numberInQueue1 == 0){
			insertEvent(READY_FIRST_QUEUE, time +randExpNumber(2.1));
		}

		
		insertEvent(ARRIVAL_FIRST_QUEUE, time + 1);

		if (numberInQueue1 < 10){
			numberInQueue1++;
			nbrOfAccepted++;
		}else{
			nbrOfDropped++;
		}

	}
	
	private void arrivalQueue2(){
		if(numberInQueue2 == 0){
			insertEvent(READY_SECOND_QUEUE, time + 2);
		}
		numberInQueue2++;
	}
	private void readyQueue1(){
		numberInQueue1--;
		insertEvent(ARRIVAL_SECOND_QUEUE, time);
		if (numberInQueue1 > 0)
			insertEvent(READY_FIRST_QUEUE, time + randExpNumber(2.1));
	}
	private void readyQueue2(){
		numberInQueue2--;
		if(numberInQueue2 > 0){
			insertEvent(READY_SECOND_QUEUE, time +  2);
		}
	}

	
	private void measure(){
		accumulated = accumulated + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time + randExpNumber(5));
	}
}