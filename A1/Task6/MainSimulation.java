import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {

		Event actEvent;
		State actState = new State();
		Random slump = new Random();

		int count = 0;
		int runs = 10000;
		double totalTime = 0;
		boolean done = false;
		
		// The main simulation loop
		while (count < runs) {

			insertEvent(ARRIVAL, 0);

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