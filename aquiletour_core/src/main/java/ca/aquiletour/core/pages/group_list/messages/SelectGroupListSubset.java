package ca.aquiletour.core.pages.group_list.messages;

import ca.ntro.messages.NtroMessage;

public class SelectGroupListSubset extends NtroMessage {
	
	private String semesterId;
	private String courseId;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
