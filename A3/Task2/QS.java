import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public Proc sendTo;
	Random slump = new Random();
	public List<Student> allStudents;

	public void TreatSignal(Signal x){
		Student currentStudent = x.student; 
		switch (x.signalType){

			case MOVE:{
				int tmp = sameSquare(allStudents, currentStudent);
				if(tmp == -1 || currentStudent.stopTalking){
					currentStudent.stopTalking = false;
					if(currentStudent.stepsLeft > 0){

						if(currentStudent.validMove()){
							currentStudent.move();

							SignalList.SendSignal(MOVE, this, time + 1, currentStudent);

						}else{
							while(!currentStudent.validMove()){
								currentStudent.changeDirection();
							}
							currentStudent.move();
							SignalList.SendSignal(MOVE, this, time + 1, currentStudent);

						}
						
					}else{
						currentStudent.stepsLeft = currentStudent.stepToTake();
						currentStudent.changeDirection();
						while(!currentStudent.validMove()){
							currentStudent.changeDirection();
						}
						currentStudent.move();
						SignalList.SendSignal(MOVE, this, time + 1, currentStudent);

					}
				}else{
					currentStudent.talking = true;
					allStudents.get(tmp).talking = true;

					currentStudent.talkedTo[tmp] += 1.0;

					SignalList.SendSignal(TALK, this, time + 60, currentStudent);
					//System.out.println("SENT AWAY TALK COMMAND");

					System.out.println(currentStudent.uid + " has talked to " + tmp);
				}


				/* 
				System.out.println(currentStudent.uid + " got MOVE command");
				int s_uid = sameSquare(allStudents, currentStudent);
				if(currentStudent.talking){
					SignalList.SendSignal(TALK, sendTo, time + 60, currentStudent); // 1 minute
				}
				if(s_uid != -1 && !currentStudent.talking){
					currentStudent.talking = true;
					allStudents.get(s_uid).talking = true;
					//System.out.println(currentStudent.uid + " stopped to talk with " + s_uid);
					//allStudents.get(s_uid).talkedTo[currentStudent.uid] += 1;
					//currentStudent.talkedTo[s_uid] += 1;
					//System.out.println("current student: " + currentStudent.uid + " current talked to s_uid: " + s_uid + " and number: " + currentStudent.talkedTo[s_uid]);
					SignalList.SendSignal(TALK, sendTo, time + 60, currentStudent); // 1 minute
					//System.out.println(currentStudent.toString() + " " + currentStudent.metAllCounter());
					//System.exit(0);
				}else{
					*/
					
					
				//}
				
			} break;

			case TALK:{
				//System.out.println(currentStudent.uid + " got TALK command");
				if(currentStudent.talking){
					currentStudent.talking = false;
					currentStudent.stopTalking = true;
				}
				SignalList.SendSignal(MOVE, this, time+1, currentStudent);
				//System.out.println(currentStudent.uid);
				//System.out.println(currentStudent.uid + " stopped talking and should start moving");

			} break;

		}
	}
	
	private int sameSquare(List<Student> studentsList, Student currentStudent){
		int counter = 0;
		int temp = 0;
		for(Student s : studentsList){
			if(s.uid != currentStudent.uid && s.getPos()[0] == currentStudent.getPos()[0] && s.getPos()[1] == currentStudent.getPos()[1]){
				//System.out.println(s.uid + " " + currentStudent.uid);
				//System.out.println("Positions of " + s.uid + " " + s.getPos()[0] + "," + s.getPos()[1]);
				//System.out.println("Positions of " + currentStudent.uid + " " + currentStudent.getPos()[0] + "," + currentStudent.getPos()[1]);
				counter++;
				temp = s.uid;
			}
		}

		if(counter == 1){
			return temp;
		}

		return -1;
	}

	public String met_student(){
		StringBuilder sb = new StringBuilder();
		for (Student s : allStudents){
			sb.append("UID " + s.uid + " have met total of " + s.metAllCounter() + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}