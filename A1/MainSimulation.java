import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	QS Q2 = new QS(); //creates a new queue for priority customers. This simulates them "cutting" in line
    	Q1.sendTo = null;
    	Q2.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 5; //Generator generates customer with a mean inter arrival-time of 5 min
    	Random R = new Random();
    	//int prob = R.nextInt(10); // randomly creates a number between 0-9
    	
    	//if (prob < 1) {// there is a 10% chance that prob is given the value 0
    		//Generator.sendTo = Q2; //The generated customers are sent to Q2, meaning they skip the "ordinary" queue
    	//}
    	//else {
    		//Generator.sendTo = Q1;%

    	//}
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);
    	SignalList.SendSignal(MEASURE, Q2, time);


    
    	// Main loop

    	while (Q1.accumulated + Q2.accumulated < 100000) {//runs the simulations for a 1000 arrivals
    		int prob = R.nextInt(10)+1; // randomly creates a number between 1-10
        	
        	if (prob <=2) {// there is a 10% chance that prob is given the value 1. 20% that the values are 1 or 2 and 50% that they are 1-5
        		Generator.sendTo = Q2; //The generated customers are sent to Q2, meaning they skip the "ordinary" queue
        	}
        	else {
        		Generator.sendTo = Q1;

        	}
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	
    	//prints results
    	
    	System.out.println("Time: " + time);
    	System.out.println("Mean number of customers in Q1: " + 1.0*Q1.accumulated/Q1.noMeasurements);
    	System.out.println("Mean number of customers in Q2: " + 1.0*Q2.accumulated/Q2.noMeasurements);
    	System.out.println("Total number of customers in queuing system: " + 1.0*(Q1.accumulated+Q2.accumulated));
    	System.out.println("total in Q2: " + Q2.accumulated);
    	System.out.println("total in Q1: " + Q1.accumulated);
    	System.out.println("Mean waiting time in system: " + time/(Q1.accumulated+Q2.accumulated));
    	




    }
}