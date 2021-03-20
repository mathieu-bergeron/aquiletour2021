package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.server.backend.queue.QueueUpdater;
import ca.aquiletour.server.backend.queues.QueuesUpdater;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class DeleteCourseBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String queueId;
	
	public DeleteCourseBackgroundTask(User teacher, String queueId) {
		this.teacher = teacher;
		this.queueId = queueId;
	}
	
	@Override
	protected void runTask() {
		T.call(this);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		QueueUpdater.deleteQueue(modelStore, queueId);
	}

	
	private void updateStudentDashboards(ModelStoreSync modelStore, QueueModel queue) {
		T.call(this);
		
	}

	private void updateStudentDashboard(ModelStoreSync modelStore, String studentId) {
		T.call(this);

		if(modelStore.ifModelExists(DashboardModel.class, "admin", studentId)) {

			DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", studentId);
			dashboardModel.deleteCourseById(queueId);
			modelStore.save(dashboardModel);

		}else {
			Log.warning("Dashboard not found for " + studentId);
		}
	}


	@Override
	protected void onFailure(Exception e) {
		
	}
}
