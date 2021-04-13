package ca.aquiletour.core.pages.semester_list.models;

import ca.ntro.core.models.NtroModel;

public class SemesterModel implements NtroModel {
	
	private String semesterId = "";
	private ObservableSemesterDateList dates = new ObservableSemesterDateList();

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public ObservableSemesterDateList getDates() {
		return dates;
	}

	public void setDates(ObservableSemesterDateList dates) {
		this.dates = dates;
	}
}
