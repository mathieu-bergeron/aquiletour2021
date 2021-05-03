package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class SelectCurrentSemester extends NtroUserMessage<User> {
	
	private String semesterId;
	private boolean currentSemester;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public boolean getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(boolean currentSemester) {
		this.currentSemester = currentSemester;
	}
}
