import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulationRR extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();
    	

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	QS Q2 = new QS(); //creates 5 queues for customers.
    	QS Q3 = new QS();
    	QS Q4 = new QS();
    	QS Q5 = new QS();
    	Q1.sendTo = null;
    	Q2.sendTo = null;
    	Q3.sendTo = null;
    	Q4.sendTo = null;
    	Q5.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 5; //Generator generates customer with a mean inter arrival-time of 5 min
    	Random R = new Random();
    	
    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);
    	SignalList.SendSignal(MEASURE, Q2, time);
    	SignalList.SendSignal(MEASURE, Q3, time);
    	SignalList.SendSignal(MEASURE, Q4, time);
    	SignalList.SendSignal(MEASURE, Q5, time);


    
    	// Main loop
    	int i = 0;
    	int a = 0;
    	while (i < 100000) {//runs the simulations for 100000 sec
    		i++;
    		a++; // depending on the value of a the customer will be sent to a different queue, when a = 5, a resets and the loop starts over.
    		//RoundRobin
    		if (a == 1){
    			Generator.sendTo = Q1;
    			break;
    		} else if (a == 2) {
    			Generator.sendTo = Q2;
    			break;
    		} else if (a == 3) {
    			Generator.sendTo = Q3;
    			break;
    		} else if (a == 4) {
    			Generator.sendTo = Q4;
    			break;
    		} else {
    			Generator.sendTo = Q5;
    			a = 0;
    		}
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	
    	//prints results
    	
    	System.out.println("Time: " + time);
    	System.out.println("Mean number of jobs in Q1: " + 1.0*Q1.accumulated/Q1.noMeasurements);
    	System.out.println("Mean number of jobs in Q2: " + 1.0*Q2.accumulated/Q2.noMeasurements);
    	System.out.println("Mean number of jobs in Q3: " + 1.0*Q3.accumulated/Q3.noMeasurements);
    	System.out.println("Mean number of jobs in Q4: " + 1.0*Q4.accumulated/Q4.noMeasurements);
    	System.out.println("Mean number of jobs in Q5: " + 1.0*Q5.accumulated/Q5.noMeasurements);
    	



    }
}