package ca.aquiletour.core.pages.course_list.messages;

import ca.ntro.messages.NtroMessage;

public class SelectSemesterMessage extends NtroMessage {
	
	private String semesterId;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}
}
