package ca.aquiletour.core.pages.queue.messages;

import ca.ntro.messages.NtroMessage;

public class ShowQueueMessage extends NtroMessage {
	
	private String courseId;
	private String groupId;

	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
