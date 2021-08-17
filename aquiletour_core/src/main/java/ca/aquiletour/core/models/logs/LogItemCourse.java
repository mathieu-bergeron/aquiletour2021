package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.trace.T;

public class LogItemCourse extends LogItemTask {
	
	private List<String> eventItems = new ArrayList<>();


	public List<String> getEventItems() {
		return eventItems;
	}

	public void setEventItems(List<String> eventItems) {
		this.eventItems = eventItems;
	}

	@Override
	public void writeCsvLine(String separator, StringBuilder builder, int longuestTaskPath) {
		T.call(this);
		
		writeCsvLineBasicInfo(separator, builder);
		writeCsvLineGroupId(separator, builder);
		writeCsvLineTaskPath(separator, builder, longuestTaskPath);

		for(String eventItem : eventItems) {

			builder.append(separator);
			builder.append("\"");
			builder.append(eventItem);
			builder.append("\"");
		}
	}
}
