package ca.aquiletour.core.pages.dashboard.student.models;

import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.StoredAquiletourDate;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.system.trace.T;

public class CurrentTaskStudent extends CurrentTask {
	
	private StoredAquiletourDate endTime;

	public StoredAquiletourDate getEndTime() {
		return endTime;
	}

	public void setEndTime(StoredAquiletourDate endTime) {
		this.endTime = endTime;
	}

	public void updateEndTime(CourseDate endTime) {
		T.call(this);

		this.endTime.set(endTime);
	}
}
