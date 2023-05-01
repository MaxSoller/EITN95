import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal ör den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//Hör nedan skapas de processinstanser som beh�vs och parametrar i dem ges vörden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 1/5; //Generator ska generera nio kunder per sekund  //Generator shall generate 9 customers per second
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till kösystemet QS  // The generated customers shall be sent to Q1

    	//Hör nedan skickas de första signalerna för att simuleringen ska komma igöng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (Q1.prioArrival + Q1.normalArrival < 1000 ){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:
		System.out.println(Q1.accumulatedTime);
		System.out.println("Time: " + time);
    	System.out.println("Mean time in system for priority customer: " + 1.0*Q1.accumulatedPrioTime/Q1.noOfPrio);
		System.out.println("Mean time in system for normal customer: " + 1.0*Q1.accumulatedTime/Q1.noOfNormal);
    }
}