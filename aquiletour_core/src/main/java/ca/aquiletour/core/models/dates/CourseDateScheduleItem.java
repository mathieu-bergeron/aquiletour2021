package ca.aquiletour.core.models.dates;

import ca.ntro.core.system.trace.T;

public class CourseDateScheduleItem extends CourseDate {

	private String scheduleItemId = "";

	public CourseDateScheduleItem() {
		T.call(this);
	}

	public CourseDateScheduleItem(int semesterWeek, String scheduleItemId) {
		super(semesterWeek);
		T.call(this);
		
		this.scheduleItemId = scheduleItemId;
	}

	public String getScheduleItemId() {
		return scheduleItemId;
	}

	public void setScheduleItemId(String scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}
}
