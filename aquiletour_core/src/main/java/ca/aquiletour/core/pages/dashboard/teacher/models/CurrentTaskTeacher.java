package ca.aquiletour.core.pages.dashboard.teacher.models;

import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.system.trace.T;

public class CurrentTaskTeacher extends CurrentTask {
	
	private StoredInteger numberOfStudents = new StoredInteger();

	public StoredInteger getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(StoredInteger numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public void updateNumberOfStudents(int numberOfStudents) {
		T.call(this);
		
		getNumberOfStudents().set(numberOfStudents);
	}
}
