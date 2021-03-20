package ca.aquiletour.server.backend.queues;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class TeacherClosesQueueHandler extends BackendMessageHandler<TeacherClosesQueueMessage> {
	
	@Override
	public void handle(ModelStoreSync modelStore, TeacherClosesQueueMessage message) {
		T.call(this);
		
		User teacher = (User) message.getUser();
		String courseId = message.getCourseId();

		if(modelStore.ifModelExists(DashboardModel.class, teacher.getAuthToken(), teacher.getId())) {

			DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", teacher.getId());

			dashboardModel.setTeacherAvailability(false, courseId);
			modelStore.save(dashboardModel);

		}else {
			
			Log.warning("Dashboard not found for " + teacher.getId());
		}

		Ntro.threadService().executeLater(new TeacherClosesQueueBackgroundTask(teacher, courseId));
	}
}
