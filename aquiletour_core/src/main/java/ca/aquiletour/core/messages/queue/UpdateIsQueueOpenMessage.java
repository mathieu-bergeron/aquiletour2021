package ca.aquiletour.core.messages.queue;

import ca.aquiletour.core.messages.course.CourseGroupMessage;
import ca.ntro.core.system.trace.T;

public class UpdateIsQueueOpenMessage extends CourseGroupMessage {
	
	private boolean isQueueOpen = false;

	public boolean getIsQueueOpen() {
		return isQueueOpen;
	}

	public void setIsQueueOpen(boolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}

	public String queueId() {
		T.call(this);
		
		return getTeacherId();
	}
}
