package ca.aquiletour.core.models.courses.base;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class TaskPath extends Path {

	public TaskPath() {
		super();
		T.call(this);
	}

	public TaskPath(String string) {
		super(string);
		T.call(this);
	}
}
