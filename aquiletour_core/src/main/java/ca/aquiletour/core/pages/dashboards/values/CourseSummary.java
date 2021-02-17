package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CourseSummary extends NtroModelValue {

	private String title;
	private String id;
	private String summary;
	private String date;
	private boolean isQueueOpen;
	private String myAppointment;
	private int numberOfAppointments;
	

	public CourseSummary() {
		super();
	}
	
	public CourseSummary(String title, String id, boolean isQueueOpen, String myAppointment, int numberOfAppointments) {
		super();
		T.call(this);
		this.title = title;
		this.id = id;
		this.isQueueOpen = isQueueOpen;
		this.myAppointment = myAppointment;
		this.numberOfAppointments = numberOfAppointments;
	}

	

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public CourseSummary(String title, String summary, String date, String courseId) {
		super();
		this.title = title;
		this.summary = summary;
		this.date = date;
		this.id = courseId;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourseId() {
		return id;
	}

	public void setCourseId(String id) {
		this.id = id;
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
