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

		double delay = 1.0;
		double speed = 2.0;

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;
		LinkedList<Student> students = new LinkedList<Student>();
		
		for(int i = 0; i<20; i++){
			Student tmpStudent = new Student(delay, speed, i);
			tmpStudent.startPos();
			while(occupied(students, tmpStudent)){
				//System.out.println(occupied(students, tmpStudent));
				tmpStudent.startPos();
			}
			students.add(tmpStudent);
			SignalList.SendSignal(MOVE, Q1, time, tmpStudent);
		}
		Q1.allStudents = students;
    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	// Detta �r simuleringsloopen:
    	// This is the main loop
		boolean running = true;
    	while (running){
    		actSignal = SignalList.FetchSignal();
			//System.out.println(students.get(actSignal.student.uid).toString());
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);

			//System.out.println(Q1.met_student());
			int cc = 0;
			for(Student s : students){
				int count = s.metAllCounter();
				System.out.println(count);
				//System.out.println(s);
				//System.out.println(s + " have met total of: " + count);
				if(count == 19){
					cc++;
				}
			}
			
			if (cc == 20){
				running = false;
			}
			//System.out.println("Ett varv");
			// System.out.println("UID: " + actSignal.student.uid);
			// System.out.println(students.get(actSignal.student.uid).toString());
			// System.exit(0);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:
		for(Student s : students){
			System.out.println(s.getAllMettingTimes());
		}
		System.out.println("simulation done boi");

    }

	public static boolean occupied(LinkedList<Student> studentlist, Student current){
			for(Student stud : studentlist){
				if(stud.getPos() == current.getPos()){
					return true; 
				}
			}
		return false;
	}
}