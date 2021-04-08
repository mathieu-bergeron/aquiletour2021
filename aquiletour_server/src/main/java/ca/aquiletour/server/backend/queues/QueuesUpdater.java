package ca.aquiletour.server.backend.queues;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListModel;
import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class QueuesUpdater {
	
	static {
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		if(!modelStore.ifModelExists(OpenQueueListModel.class, "admin", "allQueues")) {
			modelStore.createModel(OpenQueueListModel.class, "admin", "allQueues", m -> {});
		}

		if(!modelStore.ifModelExists(OpenQueueListModel.class, "admin", "openQueues")) {
			modelStore.createModel(OpenQueueListModel.class, "admin", "openQueues", m -> {});
		}
	}
	

	public static void createQueue(ModelStoreSync modelStore,
			                       String queueId,
			                       User teacher) {

		T.call(QueuesUpdater.class);

		modelStore.updateModel(OpenQueueListModel.class, 
							   teacher.getAuthToken(), 
							   "allQueues", 
							   new ModelUpdater<OpenQueueListModel>() {
			@Override
			public void update(OpenQueueListModel allQueues) {
				T.call(this);

				allQueues.addQueueToList(newQueueSummary(queueId, teacher));
			}
		});
	}

	private static OpenQueue newQueueSummary(String queueId, User teacher) {
		T.call(OpenQueueListModel.class);

		OpenQueue queueSummary = new OpenQueue();
		queueSummary.setId(queueId);
		queueSummary.setTeacherName(teacher.getName());
		queueSummary.setTeacherSurname(teacher.getSurname());

		return queueSummary;
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

		modelStore.updateModel(OpenQueueListModel.class, 
				               "admin", 
				               queueStoreId,
				               new ModelUpdater<OpenQueueListModel>() {
									@Override
									public void update(OpenQueueListModel queueStore) {
										T.call(this);

										queueStore.deleteQueue(queueId);
									}
		});
	}

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) {

		T.call(QueuesUpdater.class);

		OpenQueueListModel allQueues = modelStore.getModel(OpenQueueListModel.class, "admin", "allQueues");
		OpenQueue summary = allQueues.findQueueByQueueId(queueId);
		modelStore.closeWithoutSaving(allQueues);
		
		modelStore.updateModel(OpenQueueListModel.class, 
				               "admin", 
				               "openQueues", 
				               new ModelUpdater<OpenQueueListModel>() {

			@Override
			public void update(OpenQueueListModel openQueues) {
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
