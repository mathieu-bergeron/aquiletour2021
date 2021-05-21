package ca.aquiletour.core.models.courses.status;

import ca.ntro.core.system.trace.T;

public class StatusTodo extends TaskStatus {

	@Override
	public boolean isTodo() {
		T.call(this);

		return true;
	}

}
