package ca.aquiletour.core.pages.course.messages;

import ca.ntro.core.Path;
import ca.ntro.messages.NtroMessage;

public class ShowTaskMessage extends NtroMessage {
	
	private Path taskPath = Path.fromRawPath("/");
	private String groupId;

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
