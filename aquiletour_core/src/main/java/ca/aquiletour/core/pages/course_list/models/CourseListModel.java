package ca.aquiletour.core.pages.course_list.models;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();
	private ObservableCourseDescriptionList courses = new ObservableCourseDescriptionList();

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}

	public ObservableCourseDescriptionList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseDescriptionList courses) {
		this.courses = courses;
	}


	public void addCourse(CourseDescription courseDescription) {
		T.call(this);
		
		courses.addItem(courseDescription);
	}
}
