package ca.aquiletour.core.pages.course_list.models;

import ca.ntro.core.models.NtroModel;

public class CourseListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}
}
