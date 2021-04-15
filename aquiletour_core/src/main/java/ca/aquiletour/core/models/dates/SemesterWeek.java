package ca.aquiletour.core.models.dates;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class SemesterWeek implements NtroModelValue {

	private int weekId = 0;                                 // typically 1-15
	private NtroDate mondayDate = new NtroDate();           
	private List<SemesterDate> days = new ArrayList<>();

	public int getWeekId() {
		return weekId;
	}

	public void setWeekId(int weekId) {
		this.weekId = weekId;
	}

	public NtroDate getMondayDate() {
		return mondayDate;
	}

	public void setMondayDate(NtroDate mondayDate) {
		this.mondayDate = mondayDate;
	}

	public List<SemesterDate> getDays() {
		return days;
	}

	public void setDays(List<SemesterDate> days) {
		this.days = days;
	}

	public void addDate(SemesterDate semesterDate) {
		T.call(this);
		
		days.add(semesterDate);
	}    
}
