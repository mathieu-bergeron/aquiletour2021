package ca.aquiletour.core.pages.course_list.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.messages.NtroUserMessage;

                                                             // FIXME: should not be necessary
public class AddCourseMessage extends NtroUserMessage<User> implements JsonSerializable {

	private CourseDescription courseDescription;
	private String semesterId;

	public CourseDescription getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(CourseDescription courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}
}
