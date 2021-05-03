package ca.aquiletour.core.pages.dashboard.models;

import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;

public abstract class CurrentTask implements NtroModelValue {
	
	private Path taskPath;

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}
	
	
	

}
