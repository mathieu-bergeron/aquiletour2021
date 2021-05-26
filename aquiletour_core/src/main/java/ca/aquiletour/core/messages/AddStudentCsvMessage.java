package ca.aquiletour.core.messages;

import ca.aquiletour.core.messages.course.CourseTaskMessage;

public class AddStudentCsvMessage extends CourseTaskMessage {
	
	private String csvFilename;
	private String csvString;
	

	public String getCsvString() {
		return csvString;
	}

	public void setCsvString(String csvString) {
		this.csvString = csvString;
	}

	public String getCsvFilename() {
		return csvFilename;
	}

	public void setCsvFilename(String csvFilename) {
		this.csvFilename = csvFilename;
	}

}
