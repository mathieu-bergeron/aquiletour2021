package ca.aquiletour.core.pages.git.commit_list;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.models.NtroModel;

public class CommitListModel implements NtroModel {

	private String semesterId = "", studentId = "", exercisePath = "", fromDate = "", toDate = "";
	private List<Commit> commits = new ArrayList<>();

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

	public List<Commit> getCommits() {
		return commits;
	}

	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}
}
