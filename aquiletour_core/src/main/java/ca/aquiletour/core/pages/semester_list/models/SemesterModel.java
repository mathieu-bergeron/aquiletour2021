package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public abstract class SemesterModel implements NtroModelValue {

	private String semesterId = Constants.DRAFTS_SEMESTER_ID;

	private SemesterSchedule semesterSchedule = new SemesterSchedule();
	
	public SemesterSchedule getSemesterSchedule() {
		return semesterSchedule;
	}

	public void setSemesterSchedule(SemesterSchedule semesterSchedule) {
		this.semesterSchedule = semesterSchedule;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public void addWeek(CalendarWeek semesterWeek) {
		T.call(this);
		
		getSemesterSchedule().addWeek(semesterWeek);
	}

	public String semesterSummary() {
		T.call(this);
		
		return getSemesterSchedule().summary();
		
	}
}
