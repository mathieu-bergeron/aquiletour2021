package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

                                                             // FIXME: should not be necessary
public class AddCourseMessage extends NtroUserMessage<User> implements JsonSerializable {

	private CourseSummary course;

	public void setCourse(CourseSummary course) {
		T.call(this);
		
		this.course = course;
	}
	
	public CourseSummary getCourse() {
		T.call(this);
		
		return course;
	}
}
