package ca.aquiletour.core.messages.git;

import ca.ntro.messages.NtroMessage;

public class RegisterExerciceMessage extends NtroMessage {
	
	private String courseId;
	private String exercicePath;
	private String directoryName;
	private String completionKeywords;
	
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
	
	public String getCompletionKeywords() {
		return completionKeywords;
	}

	public void setCompletionKeywords(String completionKeywords) {
		this.completionKeywords = completionKeywords;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

}
