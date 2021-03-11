package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class CourseSummary implements JsonSerializable {

	private String title = "";
	private String courseId = "";
	private boolean isQueueOpen;
	private boolean myAppointment;
	private int numberOfAppointments;
	

	public CourseSummary() {
		super();
	}
	
	public CourseSummary(String title, String courseId, boolean isQueueOpen,  boolean myAppointment, int numberOfAppointments) {
		super();
		T.call(this);
		this.title = title;
		this.courseId = courseId;
		this.isQueueOpen = isQueueOpen;
		this.myAppointment = myAppointment;
		this.numberOfAppointments = numberOfAppointments;
	}

	
	public CourseSummary(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Boolean getIsQueueOpen() {
		return isQueueOpen;
	}

	public void setIsQueueOpen(Boolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}

	public Boolean getMyAppointment() {
		return myAppointment;
	}

	public void setMyAppointment(Boolean myAppointment) {
		this.myAppointment = myAppointment;
	}

	public int getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
	
	
}
