package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.StoredTags;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class AppointmentRevision implements NtroModelValue {

	private String timestamp = "";
	private String coursePath = "";
	private String taskPath = "";
	private List<String> tags = new ArrayList<>();
	private String comment = "";
	private boolean modifiedByTeacher = true;

	public String getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(String coursePath) {
		this.coursePath = coursePath;
	}

	public String getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(String taskPath) {
		this.taskPath = taskPath;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void updateTimestamp(NtroDate timestamp) {
		this.timestamp = LogItem.formatTimestamp(timestamp);
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean getModifiedByTeacher() {
		return modifiedByTeacher;
	}

	public void setModifiedByTeacher(boolean modifiedByTeacher) {
		this.modifiedByTeacher = modifiedByTeacher;
	}

	public static AppointmentRevision fromAppointment(Appointment appointment) {
		T.call(AppointmentRevision.class);
		
		AppointmentRevision revision = new AppointmentRevision();
		
		revision.setTaskPath(appointment.getTaskPath().getValue().toRawPath());
		revision.setCoursePath(appointment.getCoursePath().getValue().toRawPath());
		revision.setTags(appointment.getTags().getValue());
		revision.setComment(StoredTags.removeTags(appointment.getComment().getValue()));

		return revision;
	}
}
