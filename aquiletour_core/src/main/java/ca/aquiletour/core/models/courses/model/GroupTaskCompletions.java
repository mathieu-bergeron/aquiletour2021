package ca.aquiletour.core.models.courses.model;

import ca.ntro.core.models.NtroModelValue;

public class GroupTaskCompletions implements NtroModelValue {

	private CompletionsByStudent studentCompletions = new CompletionsByStudent();

	public CompletionsByStudent getStudentCompletions() {
		return studentCompletions;
	}

	public void setStudentCompletions(CompletionsByStudent studentCompletions) {
		this.studentCompletions = studentCompletions;
	}
}
