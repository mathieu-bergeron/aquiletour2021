package ca.aquiletour.core.models.dates;

public class SemesterDate {
	
	private int semesterWeek;            // typically 1-15
	private SemesterDay semesterDay;

	public int getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(int semesterWeek) {
		this.semesterWeek = semesterWeek;
	}

	public SemesterDay getSemesterDay() {
		return semesterDay;
	}

	public void setSemesterDay(SemesterDay semesterDay) {
		this.semesterDay = semesterDay;
	}
}
