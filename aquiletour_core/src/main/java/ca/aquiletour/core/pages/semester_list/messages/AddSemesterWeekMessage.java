package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class AddSemesterWeekMessage extends NtroUserMessage<User> {
	
	private String semesterId;
	private CalendarWeek semesterWeek;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public CalendarWeek getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(CalendarWeek semesterWeek) {
		this.semesterWeek = semesterWeek;
	}
}
