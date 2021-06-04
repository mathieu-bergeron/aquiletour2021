package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.system.trace.T;

public class LogItemCourse extends LogItem {
	
	private int longuestTaskPath = 0;
	void registerLonguestTaskPath(int longuestTaskPath) {
		this.longuestTaskPath = longuestTaskPath;
	}
	
	private String groupId = "";
	private TaskPath taskPath = new TaskPath();
	private List<String> eventItems = new ArrayList<>();

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

	@Override
	protected void writeCsvLineAfterBasicInfo(String separator, StringBuilder builder) {
		T.call(this);

		builder.append(separator);
		builder.append(groupId);
		
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
