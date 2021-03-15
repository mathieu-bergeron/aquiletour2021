package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class DeleteAppointmentHandler extends BackendMessageHandler<DeleteAppointmentMessage> {

	@Override
	public void handle(ModelStoreSync modelStore,DeleteAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		String appointmentId = message.getAppointmentId();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);
		
		if(queueModel != null) {
			
			queueModel.deleteAppointment(message.getAppointmentId());
			modelStore.save(queueModel);

			String studentId = queueModel.receiveStudentIdOfAppointment(appointmentId);
			DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
					"admin",
					studentId);
			
			dashboardModel.updateMyAppointment(courseId, false);
			modelStore.save(dashboardModel);

			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					// XXX: must get a fresh copy of the modelStore (it is thread specific)
					ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
					
					List<String> studentIds = queueModel.getStudentIds();
					int nbAppointment = queueModel.getAppointments().size();

					for (String studentId : studentIds) {

						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", studentId);

						if(dashboardModel != null) {
							dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
							dashboardModel.updateMyAppointment(courseId, false);
							modelStore.save(dashboardModel);
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
