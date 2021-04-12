package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.CourseDashboard;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

                                                             // FIXME: should not be necessary
public class AddCourseMessage extends NtroUserMessage<User> implements JsonSerializable {

	private CourseDashboard course;

	public void setCourse(CourseDashboard course) {
		T.call(this);
		
		this.course = course;
	}
	
	public CourseDashboard getCourse() {
		T.call(this);
		
		return course;
	}
}
