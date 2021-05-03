package ca.aquiletour.core.pages.semester_list.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CourseGroup implements NtroModelValue {
	
	private String courseId = "";
	private String groupId = "";

	public CourseGroup() {
		T.call(this);
	}

	public CourseGroup(String courseId, String groupId) {
		T.call(this);

		this.courseId = courseId;
		this.groupId = groupId;
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

	public Path toPath() {
		T.call(this);

		Path path = new Path();
		path.addName(courseId);
		path.addName(groupId);
		return path;
	}

	@Override
	public String toString() {
		T.call(this);
		
		String path = toPath().toString();
		
		return path.substring(1);
	}
	
	public static CourseGroup fromString(String courseGroupString) {
		T.call(CourseGroup.class);
		
		Path path = new Path("/" + courseGroupString);
		CourseGroup courseGroup = new CourseGroup();
		
		courseGroup.setCourseId(path.name(0));
		courseGroup.setGroupId(path.name(1));
		
		return courseGroup;
	}
	
	
	
}
