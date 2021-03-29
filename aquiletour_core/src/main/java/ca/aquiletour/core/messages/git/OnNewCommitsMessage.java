package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;

public class OnNewCommitsMessage {

	private String courseId;
	private String semesterId;
	private String studentId;
	private String exercicePath;
	private CommitListModel commitList;

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

	public String getExercicePath() {
		return exercicePath;
	}

	public void setExercicePath(String exercicePath) {
		this.exercicePath = exercicePath;
	}

	public CommitListModel getCommitList() {
		return commitList;
	}

	public void setCommitList(CommitListModel commitList) {
		this.commitList = commitList;
	}
}
