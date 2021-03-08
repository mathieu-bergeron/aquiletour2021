package ca.aquiletour.core.pages.queues;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
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

	public void addQueueToList(QueueSummary queue) {
		boolean alreadyExists = false;
		if (queues != null) {
			for (int i = 0; i < queues.size(); i++) {
				if (queues.item(i).getId().equals(queue.getId())) {
					alreadyExists = true;
				}
			}
			if (!alreadyExists) {
				queues.addItem(queue);
			}
		}
	}
	
	public void deleteQueue(String queueId) {
		for (int i = 0; i < queues.size(); i++) {
			if (queues.item(i).getId().equals(queueId)) {
				queues.removeItem(queues.item(i));
			}
		}
	}

	public QueueSummary findQueueByQueueId(String queueId) {
		QueueSummary queueRequested = null;
		for (int i = 0; i < queues.size(); i++) {
			QueueSummary currentCourse = queues.item(i);
			if(currentCourse.getId().equals(queueId)) {
				queueRequested = currentCourse;
			}
		}
		return queueRequested;
	}

}
