package ca.aquiletour.core.pages.queue_list.models;

import ca.aquiletour.core.pages.queue.models.StoredDate;
import ca.ntro.core.Path;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredLong;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class QueueListItem implements NtroModelValue {
	
	private String queueId = "";
	private StoredString teacherDisplayName = new StoredString();
	private StoredDate lastActivity = new StoredDate();
	private StoredLong numberOfAppointments = new StoredLong();
	
	public StoredDate getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(StoredDate lastActivity) {
		this.lastActivity = lastActivity;
	}

	public StoredString getTeacherDisplayName() {
		return teacherDisplayName;
	}

	public void setTeacherDisplayName(StoredString teacherDisplayName) {
		this.teacherDisplayName = teacherDisplayName;
	}

	public StoredLong getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(StoredLong numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}

	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String id) {
		this.queueId = id;
	}
	
	

	public String subViewId() {
		T.call(this);
		
		Path path = new Path();
		path.addName("queue-list-item");
		path.addName(getQueueId());
		
		return path.toHtmlId();
	}

	public void updateNumberOfAppointments(long numberOfAppointments) {
		T.call(this);

		getNumberOfAppointments().set(numberOfAppointments);
	}

	public void updateTeacherDisplayName(String displayName) {
		T.call(this);
		
		getTeacherDisplayName().set(displayName);
	}

	public void updateLastActivity(NtroDate lastActivity) {
		T.call(this);

		getLastActivity().set(lastActivity);
	}
}
