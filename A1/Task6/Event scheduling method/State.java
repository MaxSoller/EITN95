import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	private boolean machine1 = true, machine2 = true, machine3 = true, machine4 = true, machine5 = true;

	public void treatEvent(Event x){
		switch (x.eventType){
			case MACHINE1:
				machine1 = false;
				machine2 = false;
				machine5 = false;
				break;
			case MACHINE2:
				machine2 = false;
				break;
			case MACHINE3:
				machine3 = false;
				machine4 = false;
				break;
			case MACHINE4:
				machine4 = false;
				break;
			case MACHINE5:
				machine5 = false;	
				break;
		}
	}

	public boolean isDead() {
		if (!machine1 && !machine2 && !machine3 && !machine4 && !machine5) {
			return true;
		}
		return false;
	}
}