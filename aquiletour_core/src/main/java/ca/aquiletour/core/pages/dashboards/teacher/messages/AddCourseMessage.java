package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddCourseMessage extends NtroMessage {

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
