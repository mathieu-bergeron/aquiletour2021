package ca.aquiletour.core.models.schedule;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.pages.semester_list.models.ObservableSemesterWeekList;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;

public class SemesterSchedule implements NtroModelValue  {

	private ObservableSemesterWeekList weeks = new ObservableSemesterWeekList();
	
	public ObservableSemesterWeekList getWeeks() {
		return weeks;
	}

	public void setWeeks(ObservableSemesterWeekList weeks) {
		this.weeks = weeks;
	}

	public void addWeek(CalendarWeek semesterWeek) {
		T.call(this);
		
		getWeeks().addItem(semesterWeek);
	}

	public NtroDate startDateForSemesterWeek(int semesterWeek) {
		T.call(this);
		
		NtroDate startDate = null;
		
		for(CalendarWeek week : getWeeks().getValue() ) {

			NtroDate startDateThisWeek = week.startDateForSemesterWeek(semesterWeek);
			
			if(startDate == null && startDateThisWeek != null) {

				startDate = startDateThisWeek;

			} else if(startDate != null 
					&& startDateThisWeek != null
					&& startDate.biggerThan(startDateThisWeek)){

				startDate = startDateThisWeek;
			}
		}
		
		return startDate;
	}

	public SemesterDate resolveDate(int semesterWeek, NtroDayOfWeek dayOfWeek) {
		T.call(this);
		
		SemesterDate date = null;

		for(CalendarWeek week : getWeeks().getValue() ) {

			date = week.resolveDate(semesterWeek, dayOfWeek);
			
			if(date != null) {
				break;
			}
		}

		return date;
	}

	public String summary() {
		T.call(this);

		StringBuilder builder = new StringBuilder();
		
		if(!weeks.isEmpty()) {

			builder.append(weeks.size());
			builder.append(" semaines");
		}

		return builder.toString();
	}

}
