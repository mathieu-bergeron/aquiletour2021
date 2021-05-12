package ca.aquiletour.core.pages.queue.student.messages;


import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class ModifyAppointmentComment extends NtroUserMessage<User> {

	private String queueId;
	private String comment;

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
