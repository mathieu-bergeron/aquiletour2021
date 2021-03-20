package ca.aquiletour.server.backend.queues;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class QueuesUpdater {

	public static void createQueue(ModelStoreSync modelStore,
			                       String queueId,
			                       User teacher) {

		T.call(QueuesUpdater.class);
		
		modelStore.updateModel(QueuesModel.class, 
				               teacher.getAuthToken(), 
				               "allQueues", 
				               new ModelUpdater<QueuesModel>() {
			@Override
			public void update(QueuesModel allQueues) {
				T.call(this);

				QueueSummary queueSummary = new QueueSummary();
				queueSummary.setId(queueId);
				queueSummary.setTeacherName(teacher.getName());
				queueSummary.setTeacherSurname(teacher.getSurname());
				
				allQueues.addQueueToList(queueSummary);
			}
		});
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) {

		T.call(QueuesUpdater.class);
		
		deleteQueueFrom(modelStore, "allQueues", queueId);
		deleteQueueFrom(modelStore, "openQueues", queueId);
	}
	
	private static void deleteQueueFrom(ModelStoreSync modelStore,
			                           String queueStoreId,
			                           String queueId) {
		T.call(QueuesUpdater.class);

		modelStore.updateModel(QueuesModel.class, 
				               "admin", 
				               queueStoreId,
				               new ModelUpdater<QueuesModel>() {
									@Override
									public void update(QueuesModel queueStore) {
										T.call(this);

										queueStore.deleteQueue(queueId);
									}
		});
	}

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) {

		T.call(QueuesUpdater.class);

		QueuesModel allQueues = modelStore.getModel(QueuesModel.class, "admin", "allQueues");

		QueueSummary summary = allQueues.findQueueByQueueId(queueId);
		
		modelStore.updateModel(QueuesModel.class, 
				               "admin", 
				               "openQueues", 
				               new ModelUpdater<QueuesModel>() {

			@Override
			public void update(QueuesModel openQueues) {
				T.call(this);

				openQueues.addQueueToList(summary);
			}
		});
	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) {

		T.call(QueuesUpdater.class);
		
		deleteQueueFrom(modelStore, "openQueues", queueId);
	}

}
