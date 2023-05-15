import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public double money = 0;
	private int monthlyDeposit = 5000;
	private double growth = 0.3/12 + 1; 
	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case MONTH:
				month();
				break;
			case CRASH:
				crash();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void month(){
		money += monthlyDeposit;
		money = money * growth;
		insertEvent(MONTH, time + 1);
	}
	
	
	private void crash(){
		int chance = slump.nextInt(100);
		if(chance < 10){
			money *= 0.75;
		}else if(chance < 35){
			money *= 0.5;
		}else if(chance < 50){
			money *= 0.6;
		}else{
			money *= 0.9;
		}
		
		insertEvent(CRASH, time + slump.nextDouble(4*12*2));
	}
}