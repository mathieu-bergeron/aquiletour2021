package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class CourseSummary implements NtroModelValue {

	private String title = "";
	private String courseId = "";
	private StoredBoolean isQueueOpen = new StoredBoolean();
	private StoredBoolean myAppointment = new StoredBoolean();
	private int numberOfAppointments;
	

	public CourseSummary() {
		super();
	}
	
	public CourseSummary(String title, String courseId, boolean isQueueOpen,  boolean myAppointment, int numberOfAppointments) {
		super();
		T.call(this);
		this.title = title;
		this.courseId = courseId;
		this.isQueueOpen = new StoredBoolean(isQueueOpen);
		this.myAppointment = new StoredBoolean(myAppointment);
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

	public StoredBoolean getIsQueueOpen() {
		return isQueueOpen;
	}

	public void setIsQueueOpen(StoredBoolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}

	public StoredBoolean getMyAppointment() {
		return myAppointment;
	}

	public void setMyAppointment(StoredBoolean myAppointment) {
		this.myAppointment = myAppointment;
	}

	public int getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}

	public void updateMyAppointment(Boolean newValue) {
		this.myAppointment.set(newValue);
	}

	public void updateQueueOpen(boolean availabilty) {
		this.isQueueOpen.set(availabilty);
	}
	
	
}
