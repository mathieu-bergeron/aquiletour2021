package ca.aquiletour.server.backend.queues;

import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class QueuesUpdater {

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

}
