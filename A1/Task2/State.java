import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int nbrA = 0;
	public int nbrB = 0;
	public int accumulated = 0;
	public int noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	SimpleFileWriter W = new SimpleFileWriter("task2_APrio_const.m", false);
	

	
	public int lambda = 150;
	public double xa = 0.002;
	public double xb = 0.004;
	public int d = 1;

	// The following method is called by the main program each time a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
			case A_ARRIVAL:
				a_arrival();
				break;
			case A_DEPART:
				a_depart();
				break;
			case B_ARRIVAL:
				b_arrival();
				break;
			case B_DEPART:
				b_depart();
				break;
			case MEASURE:
				measure();
				break;
		}
	}

	private double expDistribution(double mean) {
		return - mean * Math.log(1-slump.nextDouble());
	}

	private void a_arrival() {
		if (nbrA + nbrB == 0) {
			insertEvent(A_DEPART, time + xa);
		}
		nbrA++;
		insertEvent(A_ARRIVAL, time + expDistribution(1.0/lambda));
	}

	private void b_arrival() {
		if (nbrA + nbrB == 0) {
			insertEvent(B_DEPART, time + xb);
		}
		nbrB++;
	}

	private void a_depart() {
		nbrA--;
		insertEvent(B_ARRIVAL, time + expDistribution(1.0));
		//insertEvent(B_ARRIVAL, time + d);
		nextBPrio();
	}

	private void b_depart() {
		nbrB--;
		nextBPrio();
	}

	private void nextAPrio() {
		if (nbrA > 0) {
			insertEvent(A_DEPART, time + xa);
		} else if (nbrB > 0) {
			insertEvent(B_DEPART, time + xb);
		}
	}
	
	private void nextBPrio() {
		if (nbrB > 0) {
			insertEvent(B_DEPART, time + xb);
		} else if (nbrA > 0) {
			insertEvent(A_DEPART, time + xa);
		}
	}

	private void measure() {
		accumulated = accumulated + nbrA + nbrB;
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
		W.println(String.valueOf(nbrA + nbrB) + " " + String.valueOf(time));
	}
}