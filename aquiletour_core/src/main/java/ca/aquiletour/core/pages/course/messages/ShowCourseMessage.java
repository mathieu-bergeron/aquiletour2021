package ca.aquiletour.core.pages.course.messages;


import ca.aquiletour.core.messages.course.CourseTaskMessage;

public abstract class ShowCourseMessage extends CourseTaskMessage {
	
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
