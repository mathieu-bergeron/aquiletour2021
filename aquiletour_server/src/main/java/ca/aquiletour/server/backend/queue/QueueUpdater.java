package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.server.backend.queues.QueuesUpdater;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class QueueUpdater {

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) {

		T.call(QueueUpdater.class);

		QueuesUpdater.deleteQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);
		
		for(String studentId : queue.getStudentIds()) {
			
			DashboardUpdater.removeQueue(modelStore, studentId, queueId);
		}
		
		removeQueue(modelStore, queue);
	}

	private static void removeQueue(ModelStoreSync modelStore, QueueModel queue) {
		T.call(QueueUpdater.class);

		if(queue != null) {
			modelStore.delete(queue);
		}
	}

}
