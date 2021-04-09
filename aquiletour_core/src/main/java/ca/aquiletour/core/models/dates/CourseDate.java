package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;

public class CourseDate implements NtroModelValue {

	private int semesterWeek;            // typically 1-15

	public int getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(int semesterWeek) {
		this.semesterWeek = semesterWeek;
	}

}
