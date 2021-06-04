package ca.aquiletour.core.models.paths;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class TaskPath extends Path {

	public TaskPath() {
		super();
		T.call(this);
	}
	
	public static TaskPath fromRawPath(String rawPath) {
		T.call(TaskPath.class);
		
		TaskPath taskPath = new TaskPath();
		
		taskPath.copyNamesOf(Path.fromRawPath(rawPath));
		
		return taskPath;
	}

}
