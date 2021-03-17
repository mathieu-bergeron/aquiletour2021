package ca.aquiletour.core.pages.git.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class StudentSummary implements NtroModelValue {

	String studentId, lastCommit;
	boolean exerciceCompleted;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getLastCommit() {
		return lastCommit;
	}

	public void setLastCommit(String lastCommit) {
		this.lastCommit = lastCommit;
	}

	public boolean isExerciceCompleted() {
		return exerciceCompleted;
	}

	public void setExerciceCompleted(boolean exerciceCompleted) {
		this.exerciceCompleted = exerciceCompleted;
	}

}
