package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class DeleteAppointmentHandler extends BackendMessageHandler<DeleteAppointmentMessage> {

	@Override
	public void handle(ModelStoreSync modelStore,DeleteAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {
			
			queueModel.deleteAppointment(message.getAppointmentId());
			queueModel.save();
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						int nbAppointment = queueModel.getAppointments().size();
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
			                    "admin",
			                    studentId);
						if(dashboardModel != null) {
							dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
							if(requestingUser.getId().equals(studentId)) {
								dashboardModel.updateMyAppointment(courseId, false);
							}
							dashboardModel.save();
						}
					}
				}

				@Override
				protected void onFailure(Exception e) {
				}
			});			

		}else {
			
			// TODO: error handling
			
		}
	}
}
