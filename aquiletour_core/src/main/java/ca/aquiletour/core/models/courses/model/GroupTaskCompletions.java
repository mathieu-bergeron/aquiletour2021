package ca.aquiletour.core.models.courses.model;

import ca.aquiletour.core.models.courses.student.CompletionByTaskId;
import ca.aquiletour.core.models.courses.student.TaskCompletion;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class GroupTaskCompletions implements NtroModelValue {

	private CompletionsByStudent studentCompletions = new CompletionsByStudent();

	public CompletionsByStudent getStudentCompletions() {
		return studentCompletions;
	}

	public void setStudentCompletions(CompletionsByStudent studentCompletions) {
		this.studentCompletions = studentCompletions;
	}

	public void taskCompletedByStudent(Path taskPath, String studentId) {
		T.call(this);
		
		CompletionByTaskId completions = getStudentCompletions().valueOf(studentId);
		
		if(completions == null) {
			
			completions = new CompletionByTaskId();
			getStudentCompletions().putEntry(studentId, completions);
		}
		
		completions.putEntry(studentId, new TaskCompletion());
	}
}
