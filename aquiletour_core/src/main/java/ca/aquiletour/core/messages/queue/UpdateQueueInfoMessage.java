package ca.aquiletour.core.messages.queue;

import ca.aquiletour.core.messages.course.CourseGroupMessage;

public class UpdateQueueInfoMessage extends CourseGroupMessage {
	
	private String queueMessage;

	public String getQueueMessage() {
		return queueMessage;
	}
	public void setQueueMessage(String queueMessage) {
		this.queueMessage = queueMessage;
	}
}
