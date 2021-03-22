package ca.aquiletour.core.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.models.NtroModel;

public class StudentSummariesModel implements NtroModel {
	
	String semesterId, groupId, exercisePath, deadline;
	StudentSummary[] summaries;
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public StudentSummary[] getSummaries() {
		return summaries;
	}

	public void setSummaries(StudentSummary[] summaries) {
		this.summaries = summaries;
	}
	
	
}
