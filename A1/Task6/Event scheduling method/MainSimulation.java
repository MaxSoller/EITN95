import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {

		Event actEvent;
		State actState = new State();
		Random slump = new Random();

		int count = 0;
		int runs = 1000;
		double totalTime = 0;
		boolean done = false;
		
		// The main simulation loop
		while (count < runs) {

			// Inserting event at random TimeOfEvent between 1 and 5
			insertEvent(MACHINE1, (1+4*slump.nextDouble())); 
			insertEvent(MACHINE2, (1+4*slump.nextDouble()));
			insertEvent(MACHINE3, (1+4*slump.nextDouble()));
			insertEvent(MACHINE4, (1+4*slump.nextDouble()));
			insertEvent(MACHINE5, (1+4*slump.nextDouble()));  

			while(!done) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				done = actState.isDead();
			}

			count++;
			totalTime += time;

		}

		// Printing the result of the simulation, in this case a mean time until the system breaks down.
		System.out.println("Mean time until the system breaks down: " + totalTime / runs);
	}
}