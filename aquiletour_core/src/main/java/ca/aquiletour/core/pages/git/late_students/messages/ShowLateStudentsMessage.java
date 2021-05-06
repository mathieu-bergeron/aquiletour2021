package ca.aquiletour.core.pages.git.late_students.messages;

import ca.ntro.messages.NtroMessage;

public class ShowLateStudentsMessage extends NtroMessage {
	
	private String courseId, semesterId, exercisePath, groupId;
	private String deadline;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String string) {
		this.deadline = string;
	}

	public void loadExerciseInfo(ShowLateStudentsMessage message) {
		setCourseId(message.getCourseId());
		setDeadline(message.getDeadline());
		setExercisePath(message.getExercisePath());
		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
	}
}
