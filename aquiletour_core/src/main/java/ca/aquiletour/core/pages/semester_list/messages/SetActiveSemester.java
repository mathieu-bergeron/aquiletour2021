package ca.aquiletour.core.pages.semester_list.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class SetActiveSemester extends NtroUserMessage<User> {
	
	private String semesterId;
	private boolean isActive;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean currentSemester) {
		this.isActive = currentSemester;
	}
}
