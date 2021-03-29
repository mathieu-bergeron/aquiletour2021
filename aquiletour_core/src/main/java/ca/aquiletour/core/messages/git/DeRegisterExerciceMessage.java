package ca.aquiletour.core.messages.git;

import ca.ntro.messages.NtroMessage;

public class DeRegisterExerciceMessage extends NtroMessage {
	
	private String courseId;
	private String exercicePath;
	
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExercicePath() {
		return exercicePath;
	}

	public void setExercicePath(String exercicePath) {
		this.exercicePath = exercicePath;
	}
}
