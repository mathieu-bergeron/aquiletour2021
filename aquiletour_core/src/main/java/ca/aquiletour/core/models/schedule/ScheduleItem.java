package ca.aquiletour.core.models.schedule;

import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;

public class ScheduleItem implements NtroModelValue {
	
	private String scheduleItemId = "";
	private CourseGroup courseGroup = new CourseGroup();
	private NtroDayOfWeek dayOfWeek = new NtroDayOfWeek();
	private NtroTimeOfDay startTime = new NtroTimeOfDay();
	private NtroTimeOfDay endTime = new NtroTimeOfDay();

	public ScheduleItem() {
		T.call(this);
	}

	public ScheduleItem(String scheduleItemId, 
			            CourseGroup courseGroup, 
			            NtroDayOfWeek dayOfWeek, 
			            NtroTimeOfDay startTime, 
			            NtroTimeOfDay endTime) {
		T.call(this);
		
		this.scheduleItemId = scheduleItemId;
		this.courseGroup = courseGroup;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

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

	public CourseGroup getCourseGroup() {
		return courseGroup;
	}

	public void setCourseGroup(CourseGroup courseGroup) {
		this.courseGroup = courseGroup;
	}

	public String getScheduleItemId() {
		return scheduleItemId;
	}

	public void setScheduleItemId(String scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}

	public boolean matches(String courseId, String groupId, String scheduleItemId) {
		T.call(this);
		
		return getCourseGroup().getCourseId().equals(courseId)
				&& getCourseGroup().getGroupId().equals(groupId)
				&& this.scheduleItemId.equals(scheduleItemId);
	}
}
