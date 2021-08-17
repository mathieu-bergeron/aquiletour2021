package ca.aquiletour.core.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.values.ObservableStudentSummaryList;
import ca.ntro.core.models.NtroModel;

public class StudentSummariesModel implements NtroModel {
	
	String semesterId, groupId, exercisePath;
	private ObservableStudentSummaryList summaries = new ObservableStudentSummaryList();
	
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

	public ObservableStudentSummaryList getSummaries() {
		return summaries;
	}

	public void setSummaries(ObservableStudentSummaryList summaries) {
		this.summaries = summaries;
	}

	
	
}
