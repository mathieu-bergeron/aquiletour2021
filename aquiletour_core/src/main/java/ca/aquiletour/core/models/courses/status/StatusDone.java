package ca.aquiletour.core.models.courses.status;

import ca.ntro.core.system.trace.T;

public class StatusDone extends TaskStatus {

	@Override
	public boolean isDone() {
		T.call(this);
		
		return true;
	}

}
