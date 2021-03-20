package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.aquiletour.server.backend.queues.QueuesUpdater;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class QueueUpdater {

	public static void createQueue(ModelStoreSync modelStore,
								   String teacherId,
			                       String queueId) {

		T.call(QueueUpdater.class);

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

		T.call(QueueUpdater.class);

		QueuesUpdater.deleteQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		DashboardUpdater.deleteQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardUpdater.deleteQueueForUsers(modelStore, queueId, queue.getStudentIds());
	
		modelStore.delete(queue);
	}

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) {

		T.call(QueueUpdater.class);
		
		QueuesUpdater.openQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		DashboardUpdater.openQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardUpdater.openQueueForUsers(modelStore, queueId, queue.getStudentIds());

	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) {

		T.call(QueueUpdater.class);

		QueuesUpdater.closeQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		DashboardUpdater.closeQueueForUser(modelStore, queueId, queue.getTeacherId());
		
		DashboardUpdater.closeQueueForUsers(modelStore, queueId, queue.getStudentIds());
		
	}

	public static int addStudentsToQueue(ModelStoreSync modelStore, 
			                             String queueId, 
			                             List<User> studentsToAdd) {
		T.call(QueueUpdater.class);

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

		return 0;
	}
}
