package ca.aquiletour.server.backend.queues;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class TeacherClosesQueueBackgroundTask extends NtroTaskSync {
	
	private User teacher;
	private String queueId;
	
	public TeacherClosesQueueBackgroundTask(User teacher, String queueId) {
		this.teacher = teacher;
		this.queueId = queueId;
	}

	@Override
	protected void runTask() {
		T.call(this);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

		updateOpenQueues(modelStore);
		
		updateStudentDashboards(modelStore);
	}

	private void updateStudentDashboards(ModelStoreSync modelStore) {
		T.call(this);

		if(modelStore.ifModelExists(QueueModel.class, "TODO", queueId)) {
			
			QueueModel queueModel = modelStore.getModel(QueueModel.class, "TODO", queueId);
			List<String> studentIds = queueModel.getStudentIds();
			for (String studentId : studentIds) {

				updateStudentDashboard(modelStore, studentId);
			}

		}else {
			Log.warning("Queue " + queueId + " not found in collection QueueModel");
		}
	}

	private void updateStudentDashboard(ModelStoreSync modelStore, String studentId) {
		T.call(this);

		if(modelStore.ifModelExists(DashboardModel.class, "TODO", studentId)) {
			DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", studentId);

			dashboardModel.setTeacherAvailability(true, queueId);
			modelStore.save(dashboardModel);

		}else {
			
			Log.warning("Dashboard not found for " + studentId);
		}
	}

	private void updateOpenQueues(ModelStoreSync modelStore) {
		T.call(this);

		QueuesModel allQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "allQueues");
		QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");

		QueueSummary existingSummary = allQueuesModel.findQueueByQueueId(queueId);

		if(existingSummary != null) {
			openQueuesModel.addQueueToList(existingSummary);
		}else {
			Log.warning("Queue " + queueId + " not found in allQueues");
		}
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
