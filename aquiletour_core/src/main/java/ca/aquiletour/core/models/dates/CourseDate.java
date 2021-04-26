package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CourseDate implements NtroModelValue {

	private int semesterWeek;            // typically 1-15

	public CourseDate() {
		T.call(this);
	}

	public CourseDate(int semesterWeek) {
		T.call(this);
		
		this.semesterWeek = semesterWeek;
	}

	public int getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(int semesterWeek) {
		this.semesterWeek = semesterWeek;
	}

}
