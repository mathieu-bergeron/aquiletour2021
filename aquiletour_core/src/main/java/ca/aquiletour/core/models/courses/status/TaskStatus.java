package ca.aquiletour.core.models.courses.status;

import ca.ntro.core.system.trace.T;

public class TaskStatus {
	
	public boolean isDone() {
		T.call(this);
		
		return false;
	}

	public boolean isTodo() {
		T.call(this);

		return false;
	}

}
