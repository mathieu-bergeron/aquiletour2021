package ca.aquiletour.core.pages.course_list.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class CourseDescription implements NtroModelValue {
	
	private String semesterId = "";
	private String courseId = "";
	private String courseTitle = "";
	private String courseDescription = "";
	private StoredList<String> groupIds = new StoredList<>();

	public CourseDescription() {
		T.call(this);
	}

	public CourseDescription(String semesterId, String courseId, String courseTitle) {
		T.call(this);
		
		this.semesterId = semesterId;
		this.courseId = courseId;
		this.courseTitle = courseTitle;
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

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public StoredList<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(StoredList<String> groupIds) {
		this.groupIds = groupIds;
	}
	

	public void addGroup(String groupId) {
		T.call(this);
		
		String existingGroup = groupById(groupId);
		
		if(existingGroup == null) {
			getGroupIds().addItem(groupId);
		}
	}

	private String groupById(String groupId) {
		T.call(this);
		
		String result = null;
		
		for(String candidate : getGroupIds().getValue()) {
			if(candidate.equals(groupId)) {
				result = candidate;
				break;
			}
		}

		return result;
	}
	
	
}
