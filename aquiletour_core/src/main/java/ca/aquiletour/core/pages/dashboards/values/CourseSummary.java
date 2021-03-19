package ca.aquiletour.core.pages.dashboards.values;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.system.trace.T;

public class CourseSummary implements NtroModelValue {

	private String title = "";
	private String courseId = "";
	private StoredBoolean isQueueOpen = new StoredBoolean();
	private StoredBoolean myAppointment = new StoredBoolean();
	private StoredInteger numberOfStudents = new StoredInteger();

	public CourseSummary() {
		super();
		T.call(this);
	}

	public CourseSummary(String title, String courseId, boolean isQueueOpen,  boolean myAppointment, int numberOfStudents) {
		super();
		T.call(this);
		this.title = title;
		this.courseId = courseId;
		this.isQueueOpen = new StoredBoolean(isQueueOpen);
		this.myAppointment = new StoredBoolean(myAppointment);
		this.numberOfStudents = new StoredInteger(numberOfStudents);
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
}
