import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {
		FileWriter writer = new FileWriter("result.txt", true);
		FileWriter writer2 = new FileWriter("studentMeetings.txt");
		// Signallistan startas och actSignal deklareras. actSignal �r den senast
		// utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		double delay = 60.0;
		double speed = 2.0;

		LinkedList<Double> endTimes = new LinkedList<Double>();
		Map<Double, Integer> freq = new HashMap<Double, Integer>();

		// H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges
		// v�rden.
		// Here process instances are created (two queues and one generator) and their
		// parameters are given values.

		for (int j = 0; j < 250; j++) {
			QS Q1 = new QS();
			Q1.sendTo = null;

			LinkedList<Student> students = new LinkedList<Student>();

			for (int i = 0; i < 20; i++) {
				Student tmpStudent = new Student(delay, speed, i);
				tmpStudent.startPos();
				while (occupied(students, tmpStudent)) {
					tmpStudent.startPos();
				}
				students.add(tmpStudent);
				SignalList.SendSignal(MOVE, Q1, time, tmpStudent);
			}
			Q1.students = students;
			// H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
			// To start the simulation the first signals are put in the signal list

			// Detta �r simuleringsloopen:
			// This is the main loop
			boolean running = true;
			while (running) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
				int cc = 0;
				for (Student s : students) {
					int count = s.uniqueMeetingsCounter();
					if (count == 19) {
						cc++;
					}
				}

				if (cc == 20) {
					running = false;
				}
			}
			endTimes.add(time / 60);
			writer.write(Double.toString(time / 60) + "\n");
			time = 0;
			List<Student> all_students = Q1.students;
			System.out.println(endTimes.size());
			for (Student s : all_students) {
				for (double t : s.talkedTo) {
					if (!freq.containsKey(t)) {
						freq.put(t, 1);
					} else {
						freq.put(t, freq.get(t) + 1);
					}
				}
			}

		}
		for(Map.Entry<Double, Integer> val: freq.entrySet()){
			writer2.write(val.getKey()/60 + " : " + val.getValue() + "\n");
		}
		writer.close();
		writer2.close();
	}
	public static boolean occupied(LinkedList<Student> allStudents, Student s) {
		for (Student stud : allStudents) {
			if (stud.getPos() == s.getPos()) {
				return true;
			}
		}
		return false;
	}
}