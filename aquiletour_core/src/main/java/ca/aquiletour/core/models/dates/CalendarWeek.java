package ca.aquiletour.core.models.dates;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;

public class CalendarWeek implements NtroModelValue {

	private NtroDate mondayDate = new NtroDate();           
	private List<SemesterDate> days = new ArrayList<>();

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

	public NtroDate startDateForSemesterWeek(int semesterWeek) {
		T.call(this);

		NtroDate startDate = null;
		
		for(SemesterDate day : days) {
			if(day.getSemesterWeek() == semesterWeek) {
				if(startDate == null
						|| startDate.biggerThan(day.getCalendarDate()) ) {
					
					startDate = day.getCalendarDate();
				}
			}
		}
		
		return startDate;
	}

	public SemesterDate resolveDate(int semesterWeek, NtroDayOfWeek scheduleOf) {
		T.call(this);
		
		SemesterDate date = null;

		for(SemesterDate day : days) {
			if(day.getSemesterWeek() == semesterWeek
					&& day.getScheduleOf().equals(scheduleOf)) {
				
				date = day.toSemesterDate();
				break;
			}
		}

		return date;
	}    
}
