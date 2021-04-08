package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.system.trace.T;

public class DashboardCourse implements NtroModelValue {

	private String title = "";
	private String courseId = "";
	private StoredBoolean isQueueOpen = new StoredBoolean();
	private StoredBoolean myAppointment = new StoredBoolean();
	private StoredInteger numberOfStudents = new StoredInteger();
	private StoredInteger numberOfAppointments = new StoredInteger();

	public DashboardCourse() {
		super();
		T.call(this);
	}

	public DashboardCourse(String title, String courseId) {
		super();
		T.call(this);

		this.title = title;
		this.courseId = courseId;
	}
	
	public DashboardCourse(String title) {
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

	public void updateMyAppointment(Boolean newValue) {
		this.myAppointment.set(newValue);
	}

	public void updateQueueOpen(boolean availabilty) {
		this.isQueueOpen.set(availabilty);
	}

	public StoredInteger getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(StoredInteger numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public void updateNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents.set(numberOfStudents);
	}

	public StoredInteger getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(StoredInteger numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
	
	public void updateNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments.set(numberOfAppointments);
	}
}
