package ca.aquiletour.core.pages.group_list.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class GroupDescription implements NtroModelValue {
	
	private String semesterId = "";
	private String courseId = "";
	private String groupId = "";

	public GroupDescription() {
		T.call(this);
	}

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
