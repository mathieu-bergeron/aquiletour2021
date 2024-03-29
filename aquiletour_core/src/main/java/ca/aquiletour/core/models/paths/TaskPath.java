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

	public static TaskPath fromPath(Path path) {
		T.call(TaskPath.class);
		
		TaskPath taskPath = new TaskPath();

		taskPath.copyNamesOf(path);

		return taskPath;
	}

	public static TaskPath fromKey(String key) {
		T.call(Path.class);

		TaskPath taskPath = new TaskPath();

		taskPath.copyNamesOf(Path.fromKey(key));

		return taskPath;
	}
}
