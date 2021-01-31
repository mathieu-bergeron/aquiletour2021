package ca.aquiletour.core.pages.dashboard.messages;

import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddCourseMessage extends NtroMessage {

	private Course course;

	public void setCourse(Course course) {
		T.call(this);
		
		this.course = course;
	}
	
	public Course getCourse() {
		T.call(this);
		
		return course;
	}

}
