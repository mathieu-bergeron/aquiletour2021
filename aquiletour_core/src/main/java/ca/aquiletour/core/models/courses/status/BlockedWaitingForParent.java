package ca.aquiletour.core.models.courses.status;

import ca.aquiletour.core.models.courses.base.TaskPath;
import ca.ntro.core.system.trace.T;

public class BlockedWaitingForParent extends StatusBlocked {
	
	private TaskPath parentPath = new TaskPath();

	public BlockedWaitingForParent() {
		super();
		T.call(this);
	}

	public BlockedWaitingForParent(TaskPath parentPath) {
		super();
		T.call(this);
		
		this.parentPath = parentPath;
	}

	public TaskPath getParentPath() {
		return parentPath;
	}

	public void setParentPath(TaskPath parentPath) {
		this.parentPath = parentPath;
	}
}
