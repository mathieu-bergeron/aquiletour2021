package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.StoredTags;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class AppointmentRevision implements NtroModelValue {

	private NtroDate timestamp = new NtroDate();
	private CoursePath coursePath = new CoursePath();
	private TaskPath taskPath = new TaskPath();
	private List<String> tags = new ArrayList<>();
	private String comment = "";
	private boolean modifiedByTeacher = true;

	public CoursePath getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(CoursePath coursePath) {
		this.coursePath = coursePath;
	}

	public TaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(TaskPath taskPath) {
		this.taskPath = taskPath;
	}

	public NtroDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(NtroDate timestamp) {
		this.timestamp = timestamp;
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
		
		revision.setTaskPath(appointment.getTaskPath().getValue());
		revision.setCoursePath(appointment.getCoursePath().getValue());
		revision.setTags(appointment.getTags().getValue());
		revision.setComment(StoredTags.removeTags(appointment.getComment().getValue()));

		return revision;
	}
}
