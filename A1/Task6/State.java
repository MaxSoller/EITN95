import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	private boolean machine1 = true, machine2 = true, machine3 = true, machine4 = true, machine5 = true;
	private Random slump = new Random(); // This is just a random number generator

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case MACHINE1:
				machine1();
				break;
			case MACHINE2:
				machine2();
				break;
			case MACHINE3:
				machine3();
				break;
			case MACHINE4:
				machine4();
				break;
			case MACHINE5:
				machine5();
				break;
		}
	}

	public void arrival() {
		insertEvent(MACHINE1, time + (1+slump.nextDouble(4))); 
		insertEvent(MACHINE2, time + (1+slump.nextDouble(4)));
		insertEvent(MACHINE3, time + (1+slump.nextDouble(4)));
		insertEvent(MACHINE4, time + (1+slump.nextDouble(4)));
		insertEvent(MACHINE5, time + (1+slump.nextDouble(4)));  
	}

	public void machine1() {
		machine1 = false;
		insertEvent(MACHINE2, time);
		insertEvent(MACHINE5, time);
	}

	public void machine2() {
		machine2 = false;
	}

	public void machine3() {
		machine3 = false;
		insertEvent(MACHINE4, time);
	}

	public void machine4() {
		machine4 = false;
	}

	public void machine5() {
		machine5 = false;
	}

	public boolean isDead() {
		if (!machine1 && !machine2 && !machine3 && !machine4 && !machine5) {
			return true;
		}
		return false;
	}
}