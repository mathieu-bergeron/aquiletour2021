package ca.aquiletour.core.pages.queues.values;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableQueueList extends ObservableList<QueueSummary> {

	public ObservableQueueList() {
		super(new ArrayList<>());
	}

	public ObservableQueueList(List<QueueSummary> value) {
		super(value);
	}
}
