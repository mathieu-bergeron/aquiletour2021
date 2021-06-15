package ca.aquiletour.core.pages.queue_list.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

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
		
		@SuppressWarnings("unchecked")
		List<QueueListItem> items = getQueueListItemById().reduceTo(List.class, new ArrayList<>(), (queueId, queueListItem, accumulator) -> {

			accumulator.add(queueListItem);

			return accumulator;
		});
		
		items.sort((queueListItemA, queueListItemB) -> {
			int result = 0;
			
			NtroDate lastActivityA = queueListItemA.getLastActivity().getValue();
			NtroDate lastActivityB = queueListItemB.getLastActivity().getValue();
			
			if(lastActivityA.smallerThan(lastActivityB)) {

				result = +1;

			}else if(lastActivityA.biggerThan(lastActivityB)) {
				
				result = -1;
			}
			
			return result;
		});
		
		items.forEach(queueListItem -> {
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

	public void updateNumberOfAppointments(String queueId, long numberOfAppointments) {
		T.call(this);
		
		QueueListItem item = getQueueListItemById().valueOf(queueId);
		
		if(item != null) {
			
			item.updateNumberOfAppointments(numberOfAppointments);
		}
	}

	public void updateTeacherDisplayName(String queueId, String teacherDisplayName) {
		T.call(this);

		QueueListItem item = getQueueListItemById().valueOf(queueId);
		
		if(item != null) {

			item.updateTeacherDisplayName(teacherDisplayName);
		}
	}
}
