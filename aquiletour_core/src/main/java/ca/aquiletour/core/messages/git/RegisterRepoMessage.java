package ca.aquiletour.core.messages.git;

import ca.ntro.messages.NtroMessage;

public class RegisterRepoMessage extends NtroMessage {
	
	private String courseId;
	private String semesterId;
	private String groupId;
	private String studentId;
	private String exercicePath;
	private String repoUrl;

	public String getExercicePath() {
		return exercicePath;
	}

	public void setExercicePath(String exercicePath) {
		this.exercicePath = exercicePath;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}