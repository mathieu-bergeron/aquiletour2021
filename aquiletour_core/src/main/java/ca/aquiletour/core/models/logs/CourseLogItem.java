package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class CourseLogItem implements NtroModelValue {
	
	private NtroDate timestamp = new NtroDate();
	private String groupId = "";
	private String studentId = "";
	private TaskPath taskPath = new TaskPath();
	private List<String> eventItems = new ArrayList<>();

	public NtroDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(NtroDate timestamp) {
		this.timestamp = timestamp;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public TaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(TaskPath taskPath) {
		this.taskPath = taskPath;
	}
	
	public List<String> getEventItems() {
		return eventItems;
	}

	public void setEventItems(List<String> eventItems) {
		this.eventItems = eventItems;
	}

	public void writeCsvLine(int longuestTaskPath, 
			                 String separator, 
			                 StringBuilder builder) {
		T.call(this);
		
		builder.append(timestamp.format("y-m-d H:m:s"));

		builder.append(separator);
		builder.append(groupId);

		builder.append(separator);
		builder.append(studentId);
		
		for(int i = 0; i < longuestTaskPath; i++) {

			if(i < getTaskPath().nameCount()) {

				builder.append(separator);
				builder.append(getTaskPath().name(i));
			}
		}
		
		for(String eventItem : eventItems) {

			builder.append(separator);
			builder.append("\"");
			builder.append(eventItem);
			builder.append("\"");
		}
	}
}
