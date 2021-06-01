package ca.aquiletour.core.pages.git.commit_list;

import ca.aquiletour.core.pages.git.values.ObservableCommitList;
import ca.ntro.core.models.NtroModel;

public class CommitListModel implements NtroModel {

	private String semesterId = "", studentId = "", exercisePath = "", fromDate = "", toDate = "", courseId = "", groupId = "";
	private ObservableCommitList commits = new ObservableCommitList();

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

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public ObservableCommitList getCommits() {
		return commits;
	}

	public void setCommits(ObservableCommitList commits) {
		this.commits = commits;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
