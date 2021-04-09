package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;

public class SemesterDate implements NtroModelValue {
	
	private int semesterWeek;            // typically 1-15
	private SemesterDay semesterDay;     // monday-sunday

	private CalendarDate calendarDate;   // actual date

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

	public CalendarDate getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(CalendarDate calendarDate) {
		this.calendarDate = calendarDate;
	}
}
