package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class AddCourseBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String courseId;
	
	public AddCourseBackgroundTask(User teacher, String courseId) {
		this.courseId = courseId;
	}

	@Override
	protected void runTask() {
		T.call(this);

		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		QueueModel queue = modelStore.getModel(QueueModel.class, 
										   teacher.getAuthToken(),
										   courseId);
		
		if(queue != null) {
			queue.setTeacherId(teacher.getId());
			queue.setCourseId(courseId);
			modelStore.save(queue);

			updateQueueStores(modelStore, queue);
		}
	}

	private void updateQueueStores(ModelStoreSync modelStore, QueueModel newQueue) {
		T.call(this);

		QueuesModel allQueues = modelStore.getModel(QueuesModel.class, "admin", "allQueues");
		
		QueueSummary queueSummary = new QueueSummary();
	    queueSummary.setId(courseId);
	    queueSummary.setTeacherName(teacher.getName());
	    queueSummary.setTeacherSurname(teacher.getSurname());
		
		modelStore.save(allQueues);
	}

	@Override
	protected void onFailure(Exception e) {
	}

}
