package ca.aquiletour.core.pages.queue_list.models;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueueListModel implements NtroModel {
	
	QueueListItemById queueListItemById = new QueueListItemById();

	public QueueListItemById getQueueListItemById() {
		return queueListItemById;
	}

	public void setQueueListItemById(QueueListItemById queueListItemById) {
		this.queueListItemById = queueListItemById;
	}

	public void forEachItemInOrder(QueueListItemLambda lambda) {
		T.call(this);

		queueListItemById.forEachEntry((queueId, queueListItem) -> {
			lambda.onItem(queueListItem);
		});
	}

	public void addQueueListItem(QueueListItem queueListItem) {
		T.call(this);
		
		getQueueListItemById().putEntry(queueListItem.getQueueId(), queueListItem);
	}

	public void deleteQueueItem(String queueId) {
		T.call(this);
		
		getQueueListItemById().removeEntry(queueId);
	}
}
