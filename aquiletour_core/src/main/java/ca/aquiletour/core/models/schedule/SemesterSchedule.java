package ca.aquiletour.core.models.schedule;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.pages.semester_list.models.ObservableSemesterWeekList;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class SemesterSchedule implements NtroModelValue  {

	private ObservableSemesterWeekList weeks = new ObservableSemesterWeekList();
	
	public ObservableSemesterWeekList getWeeks() {
		return weeks;
	}

	public void setWeeks(ObservableSemesterWeekList weeks) {
		this.weeks = weeks;
	}

	public void addWeek(SemesterWeek semesterWeek) {
		T.call(this);
		
		getWeeks().addItem(semesterWeek);
	}

}
