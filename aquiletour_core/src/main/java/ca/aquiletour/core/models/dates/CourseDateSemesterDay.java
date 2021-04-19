package ca.aquiletour.core.models.dates;

import ca.ntro.models.NtroDayOfWeek;

public class CourseDateSemesterDay extends CourseDate {
	
	private NtroDayOfWeek semesterDay;

	public NtroDayOfWeek getSemesterDay() {
		return semesterDay;
	}

	public void setSemesterDay(NtroDayOfWeek semesterDay) {
		this.semesterDay = semesterDay;
	}

}
