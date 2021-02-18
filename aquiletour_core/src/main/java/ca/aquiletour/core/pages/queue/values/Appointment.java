package ca.aquiletour.core.pages.queue.values;

import ca.ntro.core.models.properties.NtroModelValue;

public class Appointment extends NtroModelValue {
	
	private String id;
	private String studentId;
	private String studentName;
	private String studentSurame;
	//private User user;
	public String getAppointmentId() {
		return id;
	}
	public void setAppointmentId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentSurname() {
		return studentSurame;
	}
	public void setStudentSurname(String studentSurame) {
		this.studentSurame = studentSurame;
	}
	

	
}
