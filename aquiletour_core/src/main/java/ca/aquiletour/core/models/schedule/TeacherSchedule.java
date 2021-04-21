package ca.aquiletour.core.models.schedule;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

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
}
