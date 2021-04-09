package ca.aquiletour.core.pages.semester_list.models;

import ca.ntro.core.models.NtroModel;

public class SemesterListModel implements NtroModel {
	
	private ObservableSemesterList semesters = new ObservableSemesterList();

	public ObservableSemesterList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterList semesters) {
		this.semesters = semesters;
	}
}
