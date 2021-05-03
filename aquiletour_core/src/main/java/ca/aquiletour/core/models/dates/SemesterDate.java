package ca.aquiletour.core.models.dates;

import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;

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

	public SemesterDate toSemesterDate() {
		T.call(this);
		
		SemesterDate date = new SemesterDate();

		date.setCalendarDate(getCalendarDate());
		date.setSemesterWeek(semesterWeek);
		date.setScheduleOf(scheduleOf);
		date.setSemesterDay(getSemesterDay());
		
		return date;
	}
	
	@Override
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(getCalendarDate().format("EEE d MMM HH:mm"));
		if(hasDifferentSchedule()) {
			builder.append(" (horaire du ");
			builder.append(getScheduleOf().shortName());
			builder.append(")");
		}
		
		return builder.toString();
	}

	public void adjustTime(NtroTimeOfDay time) {
		T.call(this);
		
		getCalendarDate().adjustTime(time);
	}

	


}
