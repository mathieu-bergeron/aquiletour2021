package ca.aquiletour.core.pages.git.messages;

import ca.ntro.messages.NtroMessage;

public class ShowCommitListMessage extends NtroMessage {
	
	String studentId;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	
}
