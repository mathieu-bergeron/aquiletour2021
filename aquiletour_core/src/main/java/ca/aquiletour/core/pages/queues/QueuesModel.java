package ca.aquiletour.core.pages.queues;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.aquiletour.core.pages.queues.values.ObservableQueueList;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.values.ObservableUserMap;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueuesModel extends NtroModel {
	
	private ObservableQueueList queues = new ObservableQueueList(new ArrayList<>());
	
	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public ObservableQueueList getQueues() {
		return queues;
	}

	public void setQueues(ObservableQueueList queues) {
		this.queues = queues;
	}
}
