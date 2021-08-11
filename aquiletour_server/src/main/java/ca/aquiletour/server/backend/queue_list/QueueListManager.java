package ca.aquiletour.server.backend.queue_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.aquiletour.core.pages.queue_list.models.QueueListModel;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

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
		

	private static QueueListItem createQueueListItem(String queueId, long numberOfAppointments, User teacher) {
		T.call(QueueListModel.class);

		QueueListItem queueListItem = new QueueListItem();
		queueListItem.setQueueId(queueId);
		queueListItem.updateTeacherDisplayName(teacher.displayName());
		queueListItem.updateLastActivity(Ntro.calendar().now());
		queueListItem.updateNumberOfAppointments(numberOfAppointments);

		return queueListItem;
	}

	public static void updateNumberOfAppointments(ModelStoreSync modelStore,
			                                      String queueId,
			                                      long numberOfAppointments) throws BackendError {
		T.call(QueueManager.class);

		modelStore.updateModel(QueueListModel.class, "admin", Constants.QUEUE_LIST_ID, queueListModel -> {
			
			queueListModel.updateNumberOfAppointments(queueId, numberOfAppointments);
		});
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
		
		long numberOfAppointments = modelStore.extractFromModel(QueueModel.class, "admin", queueId, Long.class, queueModel -> {

			return queueModel.getLatestAppointmentId();
		});
		
		modelStore.updateModel(QueueListModel.class, queueId, Constants.QUEUE_LIST_ID, queueListModel -> {

			queueListModel.addQueueListItem(createQueueListItem(queueId, numberOfAppointments, teacher));
		});
	}

	public static void updateLastActivity(ModelStoreSync modelStore,
			                              String queueId) throws BackendError {

		T.call(QueueListManager.class);
		
		modelStore.updateModel(QueueListModel.class, queueId, Constants.QUEUE_LIST_ID, queueListModel -> {

			queueListModel.updateLastActivity(queueId, Ntro.calendar().now());
		});
	}

	public static void updateTeacherDisplayName(ModelStoreSync modelStore, String queueId, String teacherDisplayName) throws BackendError {
		T.call(QueueListManager.class);

		modelStore.updateModel(QueueListModel.class, queueId, Constants.QUEUE_LIST_ID, queueListModel -> {
			
			queueListModel.updateTeacherDisplayName(queueId, teacherDisplayName);
		});
				
		
	}
}
