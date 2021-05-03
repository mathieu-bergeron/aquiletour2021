package ca.aquiletour.core.pages.dashboard.teacher.models;

import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.models.StoredInteger;

public class CurrentTaskTeacher extends CurrentTask {
	
	private StoredInteger numberOfStudents;

	public StoredInteger getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(StoredInteger numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}
}
