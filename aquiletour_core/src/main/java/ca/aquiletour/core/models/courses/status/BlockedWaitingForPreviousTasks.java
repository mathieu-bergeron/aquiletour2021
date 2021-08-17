package ca.aquiletour.core.models.courses.status;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class BlockedWaitingForPreviousTasks extends BlockedWaitingForTasks {

	public BlockedWaitingForPreviousTasks() {
		super();
		T.call(this);
	}

	public BlockedWaitingForPreviousTasks(List<Task> tasksTodo) {
		super(tasksTodo);
		T.call(this);
	}

	public String text() {
		return "Il manque une étape préalable";
	}

}
