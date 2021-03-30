package ca.aquiletour.core.messages.git;

import ca.ntro.core.system.trace.T;

public class StudentExerciseMessage extends ExerciseMessage {

	private String semesterId;
	private String groupId;
	private String studentId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void loadStudentExerciseInfo(StudentExerciseMessage message) {
		T.call(this);
		
		loadExerciseInfo(message);

		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
		setStudentId(message.getStudentId());
	}
}
