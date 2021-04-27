package ca.aquiletour.core.pages.course.messages;


import ca.aquiletour.core.messages.course.CourseMessage;

public class ShowCourseMessage extends CourseMessage {
	
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
