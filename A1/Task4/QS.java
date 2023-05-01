import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, noOfPrio = 0, noOfNormal = 0, prioArrival = 0, normalArrival = 0, prio = 0, normal = 0;
	public double accumulatedPrioTime = 0.0, accumulatedTime = 0.0;
	public Proc sendTo;
	private double chanceForPrio = 0.5; 
	Random slump = new Random();
	public LinkedList<Double> priorityTimes = new LinkedList<>();
	public LinkedList<Double> times = new LinkedList<>();

	private double randExpNumber(double mean){
		return Math.abs(mean * Math.log(1 - slump.nextDouble()));
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + randExpNumber(4));
				}

				if(slump.nextDouble() < chanceForPrio){
					priorityTimes.addLast(time);
					prio = prio + 1;
					prioArrival = prioArrival + 1;
				}else{
					times.addLast(time);
					normal = normal + 1;
					normalArrival = normalArrival + 1;
				}
			} break;

			case READY:{
				numberInQueue--;

				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + randExpNumber(4));
				}

				if(prio > 0){
					prio = prio - 1;
					accumulatedPrioTime = accumulatedPrioTime + (time - priorityTimes.pop());
					noOfPrio = noOfPrio + 1;

				}else if(normal > 0){
						normal = normal - 1;
						accumulatedTime = accumulatedTime + (time - times.pop());
						noOfNormal = noOfNormal + 1;

				}
			} break;

		}
	}
}