package ca.ntro.test.json;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class UserDefinedModelA extends NtroModel {

	private ObservableCourseList courses = new ObservableCourseList();

	public void emptyCourses() {
		ObservableCourseList newList = courses;
		for (int i = 0; i < newList.size(); i++) {
			CourseSummary course = newList.item(i);
			courses.removeItem(course);;
		}
	}

	public void addCourse(CourseSummary course) {
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

	public void deleteCourse(CourseSummary course) {
		T.call(this);

		courses.removeItem(course);
	}

	public void deleteCourseById(String courseId) {
		T.call(this);

		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				courses.removeItem(currentCourse);
			}
		}
	}

	public ObservableCourseList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseList courses) {
		this.courses = courses;
	}

	public void updateNbAppointmentOfCourse(String courseId, int nbAppointment) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				currentCourse.setNumberOfAppointments(nbAppointment);
			}
		}
	}

	public void updateMyAppointment(String courseId, Boolean state) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if (currentCourse.getTitle().equals(courseId)) {
				currentCourse.setMyAppointment(state);
			}
		}

	}
	
	public void setTeacherAvailability(boolean availabilty, String courseId) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSummary currentCourse = courses.item(i);
			if(currentCourse.getCourseId().equals(courseId)) {
				currentCourse.setIsQueueOpen(availabilty);
			}
		}
	}
}
