package ca.aquiletour.core.pages.queue.messages;

import ca.ntro.messages.NtroMessage;

public class ShowQueueMessage extends NtroMessage {

	private String queueId;

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
}
