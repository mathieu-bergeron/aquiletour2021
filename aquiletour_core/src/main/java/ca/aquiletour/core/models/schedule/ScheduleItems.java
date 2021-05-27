package ca.aquiletour.core.models.schedule;

import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ScheduleItems extends StoredList<ScheduleItem> {

	public String summary() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		if(!isEmpty()) {
			builder.append(size());
			builder.append(" séances");
		}
		
		return builder.toString();
	}

}
