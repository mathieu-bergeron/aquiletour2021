package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class AddCourseBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String courseId;
	
	public AddCourseBackgroundTask(User teacher, String courseId) {
		this.teacher = teacher;
		this.courseId = courseId;
	}

	@Override
	protected void runTask() {
		T.call(this);

		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		modelStore.createModel(QueueModel.class, 
				              teacher.getAuthToken(), 
				              courseId, 
				              new ModelInitializer<QueueModel>() {
			@Override
			public void initialize(QueueModel newQueue) {
				T.call(this);

				newQueue.setTeacherId(teacher.getId());
				newQueue.setCourseId(courseId);
			}
		});

		updateQueueStores(modelStore);
	}

	private void updateQueueStores(ModelStoreSync modelStore) {
		T.call(this);

		modelStore.updateModel(QueuesModel.class, 
				               teacher.getAuthToken(), 
				               "allQueues", 
				               new ModelUpdater<QueuesModel>() {
			@Override
			public void update(QueuesModel allQueues) {
				T.call(this);

				QueueSummary queueSummary = new QueueSummary();
				queueSummary.setId(courseId);
				queueSummary.setTeacherName(teacher.getName());
				queueSummary.setTeacherSurname(teacher.getSurname());
				
				allQueues.addQueueToList(queueSummary);
			}
		});
	}

	@Override
	protected void onFailure(Exception e) {
	}

}
