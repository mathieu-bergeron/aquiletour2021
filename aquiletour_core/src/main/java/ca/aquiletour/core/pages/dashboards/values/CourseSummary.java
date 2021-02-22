package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CourseSummary extends NtroModelValue {

	private String title;
	private String courseId;
	private boolean isQueueOpen;
	private String myAppointment;
	private int numberOfAppointments;
	

	public CourseSummary() {
		super();
	}
	
	public CourseSummary(String title, String courseId, boolean isQueueOpen, String myAppointment, int numberOfAppointments) {
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

	public boolean isQueueOpen() {
		return isQueueOpen;
	}

	public void setQueueOpen(boolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}

	public String getMyAppointment() {
		return myAppointment;
	}

	public void setMyAppointment(String myAppointment) {
		this.myAppointment = myAppointment;
	}

	public int getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
	
	
}
