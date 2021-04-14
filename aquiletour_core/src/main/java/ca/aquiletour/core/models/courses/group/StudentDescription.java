package ca.aquiletour.core.models.courses.group;

import ca.ntro.core.models.NtroModelValue;

public class StudentDescription implements NtroModelValue {
	
	private String studentId;
	private String name;
	private String surname;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
