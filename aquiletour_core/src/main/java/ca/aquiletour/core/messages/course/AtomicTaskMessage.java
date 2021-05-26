package ca.aquiletour.core.messages.course;

public class AtomicTaskMessage extends CourseTaskMessage {
	
	private String atomicTaskId;

	public String getAtomicTaskId() {
		return atomicTaskId;
	}

	public void setAtomicTaskId(String atomicTaskId) {
		this.atomicTaskId = atomicTaskId;
	}
}
