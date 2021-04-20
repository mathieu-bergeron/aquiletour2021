package ca.aquiletour.core.pages.course_list.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.messages.NtroUserMessage;

                                                             // FIXME: should not be necessary
public class AddCourseMessage extends NtroUserMessage<User> implements JsonSerializable {

	private CourseItem courseDescription;
	private String semesterId;

	public CourseItem getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(CourseItem courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}
}
