package ca.aquiletour.core.pages.git.student_summaries.messages;

import ca.ntro.messages.NtroMessage;

public class ShowStudentSummariesMessage extends NtroMessage {
	
	String courseBlaId, semesterId, groupId, exercisePath, deadline;

	public String getSemesterId() {
		return semesterId;
	}

	public String getCourseId() {
		return courseBlaId;
	}

	public void setCourseId(String courseBlaId) {
		this.courseBlaId = courseBlaId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public void loadExerciseInfo(ShowStudentSummariesMessage message) {
		setCourseId(message.getCourseId());
		setDeadline(message.getDeadline());
		setExercisePath(message.getExercisePath());
		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
	}
	
}
