package ca.aquiletour.core.pages.group_list.models;

import ca.ntro.core.models.StoredMap;
import ca.ntro.core.system.trace.T;

public class SemesterCourses extends StoredMap<ObservableCourseList> {

	public void addSemester(String semesterId) {
		T.call(this);
		
		if(!containsKey(semesterId)) {
			putEntry(semesterId, new ObservableCourseList());
		}
	}

	public void addCourse(String semesterId, String courseId) {
		T.call(this);

		ObservableCourseList courseList = valueOf(semesterId);
		courseList.addItem(courseId);
	}

}
