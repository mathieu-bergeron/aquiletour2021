package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class DeleteCourseBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String courseId;
	
	public DeleteCourseBackgroundTask(User teacher, String courseId) {
		this.teacher = teacher;
		this.courseId = courseId;
	}
	
	@Override
	protected void runTask() {
		T.call(this);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				teacher.getAuthToken(),
				courseId);
		
		updateQueueStores(modelStore);

		updateStudentDashboards(modelStore, queue);

		removeQueue(modelStore, queue);
	}

	private void updateQueueStores(ModelStoreSync modelStore) {
		T.call(this);
		
		modelStore.updateModel(QueuesModel.class, 
							   "admin",
				               "allQueues", 
				               new ModelUpdater<QueuesModel>() {
									@Override
									public void update(QueuesModel allQueues) {
										T.call(this);

										allQueues.deleteQueue(courseId);
									}
		});

		modelStore.updateModel(QueuesModel.class, 
				               "admin", 
				               "openQueues", 
				               new ModelUpdater<QueuesModel>() {
									@Override
									public void update(QueuesModel openQueues) {
										T.call(this);

										openQueues.deleteQueue(courseId);
									}
		});
	}
	
	private void updateStudentDashboards(ModelStoreSync modelStore, QueueModel queue) {
		T.call(this);
		
		for(String studentId : queue.getStudentIds()) {
			updateStudentDashboard(modelStore, studentId);
		}
	}

	private void updateStudentDashboard(ModelStoreSync modelStore, String studentId) {
		T.call(this);

		if(modelStore.ifModelExists(DashboardModel.class, "admin", studentId)) {

			DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", studentId);
			dashboardModel.deleteCourseById(courseId);
			modelStore.save(dashboardModel);

		}else {
			Log.warning("Dashboard not found for " + studentId);
		}
	}

	private void removeQueue(ModelStoreSync modelStore, QueueModel queue) {
		if(queue != null) {
			modelStore.delete(queue);
		}
	}

	@Override
	protected void onFailure(Exception e) {
		
	}
}
