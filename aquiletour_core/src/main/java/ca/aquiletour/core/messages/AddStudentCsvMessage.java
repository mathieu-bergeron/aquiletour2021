package ca.aquiletour.core.messages;

import ca.aquiletour.core.models.users.User;
import ca.ntro.messages.NtroUserMessage;

public class AddStudentCsvMessage extends NtroUserMessage<User> {
	
	private String csvString, queueId;
	

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
}
