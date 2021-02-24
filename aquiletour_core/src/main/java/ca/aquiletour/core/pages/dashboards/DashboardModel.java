package ca.aquiletour.core.pages.dashboards;

import java.util.ArrayList;

import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class DashboardModel extends NtroModel {
	private static final long serialVersionUID = -7945536015118582973L;

	private ObservableCourseList courses = new ObservableCourseList(new ArrayList<>());
	
	@Override
	public void initializeStoredValues() {
	}

	public void addCourse(CourseSummary course) {
		T.call(this);
		
		courses.addItem(course);
	}

	public ObservableCourseList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseList courses) {
		this.courses = courses;
	}
	
	public void updateNbAppointmentOfCourse(String courseId, int nbAppointment) {
		for (int i = 0; i < courses.size(); i++) {
				CourseSummary currentCourse =  courses.getItem(i);
				if(currentCourse.getTitle().equals(courseId)) {
					T.here();
					currentCourse.setNumberOfAppointments(nbAppointment);
				};
			
		}
	}

	public void updateMyAppointment(String courseId, Boolean state) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse =  courses.getItem(i);
			if(currentCourse.getTitle().equals(courseId)) {
				T.here();
				currentCourse.setMyAppointment(state);
			};
	    }
	}
}
