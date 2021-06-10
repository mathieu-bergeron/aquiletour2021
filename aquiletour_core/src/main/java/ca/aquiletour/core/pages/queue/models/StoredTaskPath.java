package ca.aquiletour.core.pages.queue.models;

import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class StoredTaskPath extends StoredProperty<TaskPath> {
	
	public StoredTaskPath() {
		super(new TaskPath());
		T.call(this);
	}

}
