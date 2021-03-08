package ca.aquiletour.core.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroMessage;

public class AddStudentCsvMessage extends NtroMessage {
	
	private String csvString, queueId;
	private User user;
	

	public String getCsvString() {
		return csvString;
	}

	public void setCsvString(String csvString) {
		this.csvString = csvString;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
