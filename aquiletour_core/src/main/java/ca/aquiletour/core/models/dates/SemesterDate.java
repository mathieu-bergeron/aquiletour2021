package ca.aquiletour.core.models.dates;

import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;

public class SemesterDate extends AquiletourDate {
	
	private int semesterWeek;                                    // typically 1-15

	private NtroDayOfWeek semesterDay = new NtroDayOfWeek();     // monday-friday
	private NtroDayOfWeek scheduleOf  = new NtroDayOfWeek();     // monday-friday

	private NtroDate calendarDate = new NtroDate();              // actual date, when known

	public int getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(int semesterWeek) {
		this.semesterWeek = semesterWeek;
	}

	public NtroDayOfWeek getSemesterDay() {
		return semesterDay;
	}

	public void setSemesterDay(NtroDayOfWeek semesterDay) {
		this.semesterDay = semesterDay;
	}

	public NtroDayOfWeek getScheduleOf() {
		return scheduleOf;
	}

	public void setScheduleOf(NtroDayOfWeek scheduleOf) {
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

	public void resolveDatesForSemester() {
		
	}
	


}
