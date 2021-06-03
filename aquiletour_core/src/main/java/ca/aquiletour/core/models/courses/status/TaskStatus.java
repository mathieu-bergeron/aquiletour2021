package ca.aquiletour.core.models.courses.status;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class TaskStatus implements NtroModelValue {
	
	private NtroDate timestamp = new NtroDate();
	
	public NtroDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(NtroDate timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isDone() {
		T.call(this);
		
		return false;
	}

	public boolean isTodo() {
		T.call(this);

		return false;
	}

	public boolean isBlocked() {
		T.call(this);

		return false;
	}

}
