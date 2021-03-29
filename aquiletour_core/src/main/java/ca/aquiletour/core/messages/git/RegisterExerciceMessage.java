package ca.aquiletour.core.messages.git;

import ca.ntro.messages.NtroMessage;

public class RegisterExerciceMessage extends NtroMessage {
	
	private String courseId;
	private String exercicePath;
	private String sourceDirectory;
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
	public String getSourceDirectory() {
		return sourceDirectory;
	}
	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}
	public String getCompletionKeywords() {
		return completionKeywords;
	}
	public void setCompletionKeywords(String completionKeywords) {
		this.completionKeywords = completionKeywords;
	}

}
