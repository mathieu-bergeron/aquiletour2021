package ca.aquiletour.core.models.courses.teacher;

import ca.ntro.core.models.NtroModelValue;

public class GroupTaskCompletions implements NtroModelValue {

	private CompletionsByStudent studentCompletions = new CompletionsByStudent();
	private InfoByStudent students = new InfoByStudent();

	public CompletionsByStudent getStudentCompletions() {
		return studentCompletions;
	}

	public void setStudentCompletions(CompletionsByStudent studentCompletions) {
		this.studentCompletions = studentCompletions;
	}

	public InfoByStudent getStudents() {
		return students;
	}

	public void setStudents(InfoByStudent students) {
		this.students = students;
	}
}
