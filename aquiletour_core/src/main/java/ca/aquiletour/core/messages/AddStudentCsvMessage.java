package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroMessage;

public class AddStudentCsvMessage extends NtroMessage {
	
	private String csvString;

	public String getCsvString() {
		return csvString;
	}

	public void setCsvString(String csvString) {
		this.csvString = csvString;
	}
}
