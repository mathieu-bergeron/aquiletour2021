package ca.aquiletour.server.backend.open_queues_list;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListModel;
import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class QueuesUpdater {
	
	static {
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		try {
			if(!modelStore.ifModelExists(OpenQueueListModel.class, "admin", "allQueues")) {
					modelStore.createModel(OpenQueueListModel.class, "admin", "allQueues", m -> {});
			}
		} catch (BackendError e) {
			Log.error("Could not initialize allQueues " + e.getMessage());
		}

		try {
			if(!modelStore.ifModelExists(OpenQueueListModel.class, "admin", "openQueues")) {
					modelStore.createModel(OpenQueueListModel.class, "admin", "openQueues", m -> {});
			}
		} catch (BackendError e) {
			Log.error("Could not initialize openQueues " + e.getMessage());
		}
	}
	

	public static void createQueue(ModelStoreSync modelStore,
			                       String queueId,
			                       User teacher) throws BackendError {

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
		queueSummary.setTeacherName(teacher.getFirstname());
		queueSummary.setTeacherSurname(teacher.getLastname());

		return queueSummary;
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) throws BackendError {

		T.call(QueuesUpdater.class);
		
		deleteQueueFrom(modelStore, "allQueues", queueId);
		deleteQueueFrom(modelStore, "openQueues", queueId);
	}
	
	private static void deleteQueueFrom(ModelStoreSync modelStore,
			                           String queueStoreId,
			                           String queueId) throws BackendError {
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
			                     String queueId) throws BackendError {

		T.call(QueuesUpdater.class);

		OpenQueue summary = modelStore.extractFromModel(OpenQueueListModel.class, 
				                                   "admin", 
				                                   "allQueues", 
				                                   OpenQueue.class,
				                                   allQueues -> {

			return allQueues.findQueueByQueueId(queueId);
		});

		modelStore.updateModel(OpenQueueListModel.class, 
							   "admin", 
							   "openQueues", 
							   openQueues -> {

			openQueues.addQueueToList(summary);
	   });
	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) throws BackendError {

		T.call(QueuesUpdater.class);
		
		deleteQueueFrom(modelStore, "openQueues", queueId);
	}

}
