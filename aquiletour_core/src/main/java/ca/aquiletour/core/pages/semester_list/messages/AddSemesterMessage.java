package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class AddSemesterMessage extends NtroUserMessage<User> {
	
	private String semesterId;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}
}
