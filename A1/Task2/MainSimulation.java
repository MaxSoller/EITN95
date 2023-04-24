import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State(); // The state that shoud be used
		
		// Some events must be put in the event list at the beginning
		insertEvent(A_ARRIVAL, 0);
		insertEvent(MEASURE, 5);

		// The main simulation loop
		while (actState.noMeasurements < 5000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Mean: " + 1.0*actState.accumulated/actState.noMeasurements);
    	System.out.println("Accumulated: " + actState.accumulated);
    	System.out.println("Nbr of Measurments: " + actState.noMeasurements);
    	actState.W.close();
	}
}