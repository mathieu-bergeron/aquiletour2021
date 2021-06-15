package ca.aquiletour.core.pages.queue_list.models;

import ca.aquiletour.core.models.user.User;
import ca.ntro.core.Path;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class QueueListItem implements JsonSerializable {
	
	private String queueId;
	private String teacherDisplayName;
	private int numberOfAnswersToDate;

	public String getTeacherDisplayName() {
		return teacherDisplayName;
	}

	public void setTeacherDisplayName(String teacherDisplayName) {
		this.teacherDisplayName = teacherDisplayName;
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

	public String subViewId() {
		T.call(this);
		
		Path path = new Path();
		path.addName("queue-list-item");
		path.addName(getQueueId());
		
		return path.toHtmlId();
	}
}
