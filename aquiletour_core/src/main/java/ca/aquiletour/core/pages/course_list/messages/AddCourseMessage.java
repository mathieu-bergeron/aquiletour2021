package ca.aquiletour.core.pages.course_list.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;

public class AddCourseMessage extends CourseTaskMessage {

	private CourseListItem courseListItem;

	public CourseListItem getCourseListItem() {
		return courseListItem;
	}

	public void setCourseListItem(CourseListItem courseListItem) {
		this.courseListItem = courseListItem;
	}
}
