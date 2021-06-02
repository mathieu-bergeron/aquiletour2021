package ca.aquiletour.core.models.courses.status;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class BlockedWaitingForSubTasks extends BlockedWaitingForTasks {

	public BlockedWaitingForSubTasks() {
		super();
		T.call(this);
	}

	public BlockedWaitingForSubTasks(List<Task> tasksTodo) {
		super(tasksTodo);
		T.call(this);
	}

	public String text() {
		return "Il manque une sous-étape";
	}
}
