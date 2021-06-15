package ca.aquiletour.server.backend.open_queues_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.aquiletour.core.pages.queue_list.models.QueueListModel;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import jsweet.util.StringTypes.del;

public class QueuesUpdater {
	
	static {
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		try {
			if(!modelStore.ifModelExists(QueueListModel.class, "admin", Constants.QUEUE_LIST_ID)) {
					modelStore.createModel(QueueListModel.class, "admin", Constants.QUEUE_LIST_ID, m -> {});
			}
		} catch (BackendError e) {
			Log.error("Could not initialize " + Constants.QUEUE_LIST_ID + " " + e.getMessage());
		}
	}

	private static QueueListItem createQueueListItem(String queueId, User teacher) {
		T.call(QueueListModel.class);

		QueueListItem queueListItem = new QueueListItem();
		queueListItem.setQueueId(queueId);
		queueListItem.setTeacherName(teacher.getFirstname());
		queueListItem.setTeacherSurname(teacher.getLastname());

		return queueListItem;
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) throws BackendError {

		T.call(QueuesUpdater.class);
		
		modelStore.updateModel(QueueListModel.class, "admin", Constants.QUEUE_LIST_ID, new ModelUpdater<QueueListModel>() {
			@Override
			public void update(QueueListModel queueListModel) {
				T.call(this);
				
				queueListModel.deleteQueueItem(queueId);
			}
		});
	}

	public static void addQueue(ModelStoreSync modelStore,
			                    String queueId,
			                    User teacher) throws BackendError {

		T.call(QueuesUpdater.class);
		
		modelStore.readModel(QueueModel.class, queueId, "admin", queueModel -> {

			modelStore.updateModel(QueueListModel.class, queueId, "admin", queueListModel -> {
				
				queueListModel.addQueueListItem(createQueueListItem(queueId, teacher));
			});
		});
	}
}
