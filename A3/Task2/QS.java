import java.util.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public Proc sendTo;
	Random slump = new Random();
	public List<Student> students;

	public void TreatSignal(Signal x) {
		Student s = x.student;
		switch (x.signalType) {

			case MOVE: {
				int talkingFriendId = meetingStudent(students, s);
				if (talkingFriendId == -1 || s.stopTalking) {
					s.stopTalking = false;
					if (s.stepsLeft > 0) {

						if (s.validMove()) {
							s.move();

							SignalList.SendSignal(MOVE, this, time + s.timeToMove(),
									s);

						} else {
							while (!s.validMove()) {
								s.changeDirection();
							}
							s.move();
							SignalList.SendSignal(MOVE, this, time + s.timeToMove(),
									s);

						}

					} else {
						s.stepsLeft = s.nextAmountOfSteps();
						s.changeDirection();
						while (!s.validMove()) {
							s.changeDirection();
						}
						s.move();
						SignalList.SendSignal(MOVE, this, time + s.timeToMove(),
								s);

					}
				} else {
					s.talking = true;
					students.get(talkingFriendId).talking = true;

					s.talkedTo[talkingFriendId] += s.talkingTime;

					SignalList.SendSignal(TALK, this, time + s.talkingTime, s);
				}

			}
				break;

			case TALK: {
				if (s.talking) {
					s.talking = false;
					s.stopTalking = true;
				}
				SignalList.SendSignal(MOVE, this, time + s.timeToMove(),
						s);


			}
				break;

		}
	}

	private int meetingStudent(List<Student> studentsList, Student currentStudent) {
		int counter = 0;
		int talkingPartnerUID = 0;
		for (Student s : studentsList) {
			if (s.uid != currentStudent.uid && s.getPos()[0] == currentStudent.getPos()[0]
					&& s.getPos()[1] == currentStudent.getPos()[1]) {
				counter++;
				talkingPartnerUID = s.uid;
			}
		}

		if (counter == 1) {
			return talkingPartnerUID;
		}

		return -1;
	}

	public String met_student() {
		StringBuilder sb = new StringBuilder();
		for (Student s : students) {
			sb.append("UID " + s.uid + " have met total of " + s.uniqueMeetingsCounter() + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}