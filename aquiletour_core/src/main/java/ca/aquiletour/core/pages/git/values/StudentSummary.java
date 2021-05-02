package ca.aquiletour.core.pages.git.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class StudentSummary implements NtroModelValue {

	String studentId;
	int lastCommitBeforeDeadline, lastCommitAfterDealine;
	boolean exerciceCompleted, exerciseCompletedBeforeDeadline;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean isExerciceCompleted() {
		return exerciceCompleted;
	}

	public void setExerciceCompleted(boolean exerciceCompleted) {
		this.exerciceCompleted = exerciceCompleted;
	}

	public int getLastCommitBeforeDeadline() {
		return lastCommitBeforeDeadline;
	}

	public void setLastCommitBeforeDeadline(int lastCommitBeforeDeadline) {
		this.lastCommitBeforeDeadline = lastCommitBeforeDeadline;
	}

	public int getLastCommitAfterDealine() {
		return lastCommitAfterDealine;
	}

	public void setLastCommitAfterDealine(int lastCommitAfterDealine) {
		this.lastCommitAfterDealine = lastCommitAfterDealine;
	}

	public boolean isExerciseCompletedBeforeDeadline() {
		return exerciseCompletedBeforeDeadline;
	}

	public void setExerciseCompletedBeforeDeadline(boolean exerciseCompletedBeforeDeadline) {
		this.exerciseCompletedBeforeDeadline = exerciseCompletedBeforeDeadline;
	}
	
	

}
