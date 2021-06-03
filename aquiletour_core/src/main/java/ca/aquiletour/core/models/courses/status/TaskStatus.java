package ca.aquiletour.core.models.courses.status;

import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.ConcreteDate;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskStatus implements NtroModelValue {
	
	private AquiletourDate timestamp = AquiletourDate.undefined();
	
	public AquiletourDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(AquiletourDate timestamp) {
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

	public boolean shouldUpdate(TaskStatus statusUpdate) {
		T.call(this);
		
		boolean shouldUpdate = false;
		
		if(!this.getClass().equals(statusUpdate.getClass())) {

			shouldUpdate = true;

		}else if(!getTimestamp().isDefined()
				&& statusUpdate.getTimestamp().isDefined()) {
			
			shouldUpdate = true;

		}

		return shouldUpdate;
	}

}
