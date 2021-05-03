package ca.aquiletour.core.pages.course_list.messages;

import ca.aquiletour.core.messages.course.CourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;

public class AddCourseMessage extends CourseMessage {

	private CourseListItem courseListItem;

	public CourseListItem getCourseListItem() {
		return courseListItem;
	}

	public void setCourseListItem(CourseListItem courseListItem) {
		this.courseListItem = courseListItem;
	}
}
