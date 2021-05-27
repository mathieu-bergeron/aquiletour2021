package ca.aquiletour.core.models.schedule;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDayOfWeek;

public class TeacherSchedule implements NtroModel {
	
	private String semesterId = "";
	private String teacherId = "";
	private ScheduleItems scheduleItems = new ScheduleItems();

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public ScheduleItems getScheduleItems() {
		return scheduleItems;
	}

	public void setScheduleItems(ScheduleItems scheduleItems) {
		this.scheduleItems = scheduleItems;
	}

	public void addScheduleItem(ScheduleItem scheduleItem) {
		T.call(this);

		getScheduleItems().addItem(scheduleItem);
	}

	public ScheduleItem findScheduleItem(String courseId, String groupId, String scheduleItemId) {
		T.call(this);
		
		ScheduleItem item = null;
		
		for(ScheduleItem candidate : scheduleItems.getValue()) {
			if(candidate.matches(courseId, groupId, scheduleItemId)) {
				item = candidate;
				break;
			}
		}

		return item;
	}

	public String summary() {
		T.call(this);
		
		return getScheduleItems().summary();
	}
}
