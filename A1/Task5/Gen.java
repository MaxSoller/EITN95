import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc{

	//Slumptalsgeneratorn startas:
	//The random number generator is started:
	Random slump = new Random();
	private int counter = 0;
	public List<Integer> accumulatedInQueues = new ArrayList<>(Arrays.asList(0,0,0,0,0));
	
	public int noMeasurements = 0;
	//Generatorn har tv� parametrar:
	//There are two parameters:
	public LinkedList<QS> sendTo;    //Anger till vilken process de genererade kunderna ska skickas //Where to send customers
	public double lambda;  //Hur m�nga per sekund som ska generas //How many to generate per second

	//H�r nedan anger man vad som ska g�ras n�r en signal kommer //What to do when a signal arrives
	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:{
				SignalList.SendSignal(ARRIVAL, randomSendTo(sendTo), time);
				SignalList.SendSignal(READY, this, time + (2.0/lambda)*slump.nextDouble());}
				break;
			case MEASURE:{
				noMeasurements++;
				for(int i = 0; i < 5; i++){
					int tmp = accumulatedInQueues.get(i);
					 accumulatedInQueues.set(i, tmp + sendTo.get(i).numberInQueue);
				}
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
	public QS randomSendTo(LinkedList<QS> queues){
		Random slump = new Random();
		return queues.get(slump.nextInt(5));
	}

	public QS roundRobin(LinkedList<QS> queues){
		QS tmp = queues.get(counter);
		counter += 1;
		return tmp;
	}
	public QS shortestQueue(LinkedList<QS> queues){
		int smallest = Integer.MAX_VALUE;
		int current = 10;
		for(int i = 0; i < 5; i++){
			if (queues.get(i).numberInQueue < smallest){
				smallest = queues.get(i).numberInQueue;
				current = i;
			}
		}
		return queues.get(current);
	}
}