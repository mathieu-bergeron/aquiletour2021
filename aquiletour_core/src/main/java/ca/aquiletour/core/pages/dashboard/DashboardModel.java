package ca.aquiletour.core.pages.dashboard;

import java.util.ArrayList;

import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.aquiletour.core.pages.dashboard.values.ObservableCourseList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class DashboardModel extends NtroModel {
	private static final long serialVersionUID = -7945536015118582973L;

	private ObservableCourseList courses = new ObservableCourseList(new ArrayList<>());
	
	@Override
	public void initializeStoredValues() {
	}

	public void addCourse(Course course) {
		T.call(this);
		
		courses.addItem(course);
	}

	public ObservableCourseList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseList courses) {
		this.courses = courses;
	}

}
