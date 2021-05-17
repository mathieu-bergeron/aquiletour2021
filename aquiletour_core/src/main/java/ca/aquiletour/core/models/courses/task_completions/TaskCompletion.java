package ca.aquiletour.core.models.courses.task_completions;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskCompletion implements NtroModelValue {
	
	String studentId = "";
	String groupId = "";

	public TaskCompletion() {
		T.call(this);
	}

	public TaskCompletion(String studentId, String groupId) {
		T.call(this);
		
		this.studentId = studentId;
		this.groupId = groupId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
