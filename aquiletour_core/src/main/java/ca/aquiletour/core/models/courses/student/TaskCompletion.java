package ca.aquiletour.core.models.courses.student;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;

public class TaskCompletion implements NtroModelValue {
	
	private StoredBoolean completed = new StoredBoolean(false);

	public StoredBoolean getCompleted() {
		return completed;
	}

	public void setCompleted(StoredBoolean completed) {
		this.completed = completed;
	}
}
