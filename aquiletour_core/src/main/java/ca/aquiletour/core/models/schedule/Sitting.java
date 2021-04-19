package ca.aquiletour.core.models.schedule;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;

public class Sitting implements NtroModelValue {
	
	private NtroDayOfWeek dayOfWeek;
	private NtroTimeOfDay startTime;
	private NtroTimeOfDay endTime;
	private String courseId;
	private String groupId;
	private int periodId;

	public NtroDayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(NtroDayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public NtroTimeOfDay getStartTime() {
		return startTime;
	}

	public void setStartTime(NtroTimeOfDay startTime) {
		this.startTime = startTime;
	}

	public NtroTimeOfDay getEndTime() {
		return endTime;
	}

	public void setEndTime(NtroTimeOfDay endTime) {
		this.endTime = endTime;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getPeriodId() {
		return periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
}
