package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.server.backend.dashboard.DashboardModels;
import ca.aquiletour.server.backend.queues.QueuesModels;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class QueueModels {

	public static void createQueue(ModelStoreSync modelStore,
								   String queueId,
			                       User user) {

		T.call(QueueModels.class);
		
		createQueue(modelStore, user.getId(), queueId);
	}

	public static void createQueue(ModelStoreSync modelStore,
								   String teacherId,
			                       String queueId) {

		T.call(QueueModels.class);

		modelStore.createModel(QueueModel.class, 
							   "admin",
				               queueId, 
				               new ModelInitializer<QueueModel>() {
			@Override
			public void initialize(QueueModel newQueue) {
				T.call(this);

				newQueue.setTeacherId(teacherId);
				newQueue.setCourseId(queueId);
			}
		});
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) {

		T.call(QueueModels.class);

		QueuesModels.deleteQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		DashboardModels.deleteQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardModels.deleteQueueForUsers(modelStore, queueId, queue.getStudentIds());
	
		modelStore.delete(queue);
	}

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) {

		T.call(QueueModels.class);
		
		QueuesModels.openQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		DashboardModels.openQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardModels.openQueueForUsers(modelStore, queueId, queue.getStudentIds());

	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) {

		T.call(QueueModels.class);

		QueuesModels.closeQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);
		
		modelStore.updateModel(QueueModel.class, "amdin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.clearQueue();
			}
		});
		

		DashboardModels.closeQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardModels.closeQueueForUsers(modelStore, queueId, queue.getStudentIds());
		
	}

	public static int addStudentsToQueue(ModelStoreSync modelStore, 
			                             String queueId, 
			                             List<User> studentsToAdd) {
		T.call(QueueModels.class);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		int numberOfStudentAdded = 0;
		
		for(User student : studentsToAdd) {
			String studentId = student.getId();
			
			if(!queue.getStudentIds().contains(studentId)) {

				queue.getStudentIds().add(studentId);
				numberOfStudentAdded++;
			}
		}

		if(numberOfStudentAdded > 0) {
			modelStore.save(queue);
		}

		return numberOfStudentAdded;
	}
}
