package ca.aquiletour.core.pages.queues.values;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class QueueSummary implements JsonSerializable {
	
	private String id;
	private String teacherName;
	private String teacherSurname;
	//private int numberOfAppointments;
	private int numberOfAnswersToDate;
	
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherSurname() {
		return teacherSurname;
	}
	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}
//	public int getNumberOfAppointments() {
//		return numberOfAppointments;
//	}
//	public void setNumberOfAppointments(int numberOfAppointments) {
//		this.numberOfAppointments = numberOfAppointments;
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumberOfAnswersToDate() {
		return numberOfAnswersToDate;
	}
	public void setNumberOfAnswersToDate(int numberOfAnswersToDate) {
		this.numberOfAnswersToDate = numberOfAnswersToDate;
	}
}
