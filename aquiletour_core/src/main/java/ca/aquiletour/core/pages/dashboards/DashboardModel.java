package ca.aquiletour.core.pages.dashboards;


import ca.aquiletour.core.pages.dashboards.values.DashboardCourse;
import ca.aquiletour.core.pages.dashboards.values.ObservableDashboardCourseList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class DashboardModel implements NtroModel {

	// XXX: Must be initialized, otherwise we cannot properly install observers
	private ObservableDashboardCourseList courses = new ObservableDashboardCourseList();

	public void emptyCourses() {
		ObservableDashboardCourseList newList = courses;
		for (int i = 0; i < newList.size(); i++) {
			DashboardCourse course = newList.item(i);
			courses.removeItem(course);;
		}
	}

	public void addCourse(DashboardCourse course) {
		T.call(this);
		boolean alreadyExists = false;
		if (courses != null) {
			for (int i = 0; i < courses.size(); i++) {
				if (courses.item(i).getCourseId().equals(course.getCourseId())) {
					alreadyExists = true;
				}
			}
			if (!alreadyExists) {
				courses.addItem(course);
			}
		}
	}

	public void deleteCourse(DashboardCourse course) {
		T.call(this);

		courses.removeItem(course);
	}

	public void deleteCourseById(String courseId) {
		T.call(this);

		for (int i = 0; i < courses.size(); i++) {
			DashboardCourse currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				courses.removeItem(currentCourse);
			}
		}
	}

	public ObservableDashboardCourseList getCourses() {
		return courses;
	}

	public void setCourses(ObservableDashboardCourseList courses) {
		this.courses = courses;
	}

	public void updateNbAppointmentOfCourse(String courseId, int nbAppointment) {
		for (int i = 0; i < courses.size(); i++) {
			DashboardCourse currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				currentCourse.updateNumberOfAppointments(nbAppointment);
			}
		}
	}

	public void updateMyAppointment(String courseId, Boolean state) {
		for (int i = 0; i < courses.size(); i++) {
			DashboardCourse currentCourse = courses.item(i);
			if (currentCourse.getCourseId().equals(courseId)) {
				currentCourse.updateMyAppointment(state);
			}
		}
		
	}
	
	public boolean doesStudentAlreadyHaveAppointment(String courseId) {
		boolean myAppointment = false;
		for (int i = 0; i < courses.size(); i++) {
			DashboardCourse currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				myAppointment = currentCourse.getMyAppointment().getValue();
			}
		}
		return myAppointment;

	}
	
	public void setTeacherAvailability(String courseId, boolean availabilty) {
		for (int i = 0; i < courses.size(); i++) {
			DashboardCourse currentCourse = courses.item(i);
			if(currentCourse.getCourseId().equals(courseId)) {
				currentCourse.updateQueueOpen(availabilty);
			}
		}
	}

	public DashboardCourse findCourseById(String courseId) {
		DashboardCourse course = null;
		for(DashboardCourse candidate : courses.getValue()) {
			if(candidate.getCourseId().equals(courseId)) {
				course = candidate;
				break;
			}
		}
		return course;
	}
}
