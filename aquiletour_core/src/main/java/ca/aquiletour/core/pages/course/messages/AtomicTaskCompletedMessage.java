package ca.aquiletour.core.pages.course.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;

public class AtomicTaskCompletedMessage extends CourseTaskMessage {

	private String groupId;
	private String atomicTaskId;
	private AtomicTaskCompletion completion;

	public String getAtomicTaskId() {
		return atomicTaskId;
	}

	public void setAtomicTaskId(String atomicTaskId) {
		this.atomicTaskId = atomicTaskId;
	}

	public AtomicTaskCompletion getCompletion() {
		return completion;
	}

	public void setCompletion(AtomicTaskCompletion completion) {
		this.completion = completion;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
