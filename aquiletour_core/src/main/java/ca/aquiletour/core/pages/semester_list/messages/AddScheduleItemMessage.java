package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class AddScheduleItemMessage extends NtroUserMessage<User> {

	private String semesterId;
	private ScheduleItem scheduleItem;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public ScheduleItem getScheduleItem() {
		return scheduleItem;
	}

	public void setScheduleItem(ScheduleItem scheduleItem) {
		this.scheduleItem = scheduleItem;
	}
}
