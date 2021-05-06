package ca.aquiletour.core.pages.git.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class StudentSummary implements NtroModelValue {

	String studentId;
	long lastCommitBeforeDeadline, lastCommitAfterDeadline;
	boolean exerciseCompleted, exerciseCompletedBeforeDeadline;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean isExerciseCompletedBeforeDeadline() {
		return exerciseCompletedBeforeDeadline;
	}

	public void setExerciseCompletedBeforeDeadline(boolean exerciseCompletedBeforeDeadline) {
		this.exerciseCompletedBeforeDeadline = exerciseCompletedBeforeDeadline;
	}

	public boolean isExerciseCompleted() {
		return exerciseCompleted;
	}

	public void setExerciseCompleted(boolean exerciseCompleted) {
		this.exerciseCompleted = exerciseCompleted;
	}

	public long getLastCommitBeforeDeadline() {
		return lastCommitBeforeDeadline;
	}

	public void setLastCommitBeforeDeadline(long lastCommitBeforeDeadline) {
		this.lastCommitBeforeDeadline = lastCommitBeforeDeadline;
	}

	public long getLastCommitAfterDeadline() {
		return lastCommitAfterDeadline;
	}

	public void setLastCommitAfterDeadline(long lastCommitAfterDeadline) {
		this.lastCommitAfterDeadline = lastCommitAfterDeadline;
	}
	
	

}
