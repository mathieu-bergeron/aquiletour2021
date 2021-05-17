package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseMessage;

public class TaskCompletedMessage extends CourseMessage {
	
	private String atomicTaskId;

	public String getAtomicTaskId() {
		return atomicTaskId;
	}

	public void setAtomicTaskId(String atomicTaskId) {
		this.atomicTaskId = atomicTaskId;
	}
}
