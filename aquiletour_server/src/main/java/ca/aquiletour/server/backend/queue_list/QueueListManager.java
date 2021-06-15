package ca.aquiletour.server.backend.queue_list;

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

public class QueueListManager {
	
	public static void initialize(ModelStoreSync modelStore) {
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
		queueListItem.setTeacherDisplayName(teacher.displayName());

		return queueListItem;
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) throws BackendError {

		T.call(QueueListManager.class);
		
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

		T.call(QueueListManager.class);
		
		modelStore.readModel(QueueModel.class, "admin", queueId, queueModel -> {

			modelStore.updateModel(QueueListModel.class, queueId, Constants.QUEUE_LIST_ID, queueListModel -> {
				
				queueListModel.addQueueListItem(createQueueListItem(queueId, teacher));
			});
		});
	}
}
