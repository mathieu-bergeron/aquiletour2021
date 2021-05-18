package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentRepoMessage;
import ca.ntro.core.system.trace.T;

public class StudentExerciseApiMessage extends ExerciseMessage {

	private String semesterId;
	private String groupId;
	private String studentId;
	
	public StudentExerciseApiMessage() {
		super();
		T.call(this);
	}
	
	public StudentExerciseApiMessage(StudentRepoMessage message) {
		super(message);
		T.call(this);

		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
		setStudentId(message.getStudentId());
	}

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

	public void loadStudentExerciseInfo(StudentExerciseApiMessage message) {
		T.call(this);
		
		loadExerciseInfo(message);

		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
		setStudentId(message.getStudentId());
	}
}
