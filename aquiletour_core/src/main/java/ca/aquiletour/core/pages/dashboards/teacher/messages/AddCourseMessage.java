package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.DashboardCourse;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroUserMessage;

                                                             // FIXME: should not be necessary
public class AddCourseMessage extends NtroUserMessage<User> implements JsonSerializable {

	private DashboardCourse course;

	public void setCourse(DashboardCourse course) {
		T.call(this);
		
		this.course = course;
	}
	
	public DashboardCourse getCourse() {
		T.call(this);
		
		return course;
	}
}
