package ca.aquiletour.core.models.courses.atomic_tasks;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class AtomicTaskCompletion implements NtroModelValue {
	
	String atomicTaskId = "";
	String studentId = "";
	String groupId = "";

	public AtomicTaskCompletion() {
		T.call(this);
	}

	public AtomicTaskCompletion(String studentId, String groupId) {
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

	public String getAtomicTaskId() {
		return atomicTaskId;
	}

	public void setAtomicTaskId(String atomicTaskId) {
		this.atomicTaskId = atomicTaskId;
	}
	
	public boolean isCompleted() {
		T.call(this);
		
		return true;
	}
}
