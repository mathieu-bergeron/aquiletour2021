package ca.aquiletour.core.pages.queue.values;

import ca.ntro.core.json.JsonSerializable;

public class Appointment implements JsonSerializable {
	
	private String id = "";
	private String studentId = "";
	private String studentName = "";
	private String studentSurname = "";
	//private User user;

	public String getId() {
		return id;
	}
	public void setId(String id) {
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
		return studentSurname;
	}
	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}
	

	
}
