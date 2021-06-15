package ca.aquiletour.core.pages.queue_list.models;

import ca.ntro.core.Path;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class QueueListItem implements JsonSerializable {
	
	private String queueId;
	private String teacherName;
	private String teacherSurname;
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
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String id) {
		this.queueId = id;
	}
	public int getNumberOfAnswersToDate() {
		return numberOfAnswersToDate;
	}
	public void setNumberOfAnswersToDate(int numberOfAnswersToDate) {
		this.numberOfAnswersToDate = numberOfAnswersToDate;
	}

	public String htmlId() {
		T.call(this);
		
		Path path = new Path();
		path.addName("queue-list-item");
		path.addName(getQueueId());
		
		return path.toHtmlId();
	}
}
