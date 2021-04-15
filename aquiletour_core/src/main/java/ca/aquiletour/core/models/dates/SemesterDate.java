package ca.aquiletour.core.models.dates;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.models.NtroDate;

public class SemesterDate implements NtroModelValue {
	
	private int semesterWeek;                                // typically 1-15
	private SemesterDay semesterDay = new SemesterDay();     // monday-friday
	private SemesterDay scheduleOf  = new SemesterDay();     // monday-friday

	private NtroDate calendarDate;       // actual date

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

	public SemesterDay getScheduleOf() {
		return scheduleOf;
	}

	public void setScheduleOf(SemesterDay scheduleOf) {
		this.scheduleOf = scheduleOf;
	}

	public NtroDate getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(NtroDate calendarDate) {
		this.calendarDate = calendarDate;
	}

	public boolean hasDifferentSchedule() {
		return !getScheduleOf().equals(getSemesterDay());
	}
}
