import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
		int N = 100, x = 10, T = 1, M = 4000;
		double lambda = 4.0;
		String fileName = "data/task1_5.txt";
        
    	Event actEvent;
    	State actState = new State(N, x, lambda, T, fileName); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, T);

        // The main simulation loop
    	while (actState.noMeasurements  < M){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println(1.0*actState.accumulated/actState.noMeasurements);
		actState.W.close();
	}
}