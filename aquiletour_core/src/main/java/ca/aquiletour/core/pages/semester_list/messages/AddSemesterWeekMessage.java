package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class AddSemesterWeekMessage extends NtroUserMessage<User> {
	
	private String semesterId;
	private SemesterWeek semesterWeek;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public SemesterWeek getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(SemesterWeek semesterWeek) {
		this.semesterWeek = semesterWeek;
	}
}
