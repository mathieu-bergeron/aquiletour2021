package ca.aquiletour.core.pages.queues;


import ca.aquiletour.core.pages.queues.values.ObservableQueueList;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.models.NtroModel;

public class QueuesModel implements NtroModel {

	private ObservableQueueList queues = new ObservableQueueList();

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
