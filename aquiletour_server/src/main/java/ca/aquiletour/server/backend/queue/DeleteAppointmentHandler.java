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
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);
		
		if(queueModel != null) {
			
			queueModel.deleteAppointment(message.getAppointmentId());
			modelStore.save(queueModel);

			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						int nbAppointment = queueModel.getAppointments().size();

						// XXX: cannot use modelStore in executeLater
						//      must use Ntro.modelStore()
						ModelLoader modelLoader = Ntro.modelStore().getLoader(DashboardModel.class, "admin", studentId);
						modelLoader.execute();
						DashboardModel dashboardModel = (DashboardModel) modelLoader.getModel();

						if(dashboardModel != null) {
							dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
							if(requestingUser.getId().equals(studentId)) {
								dashboardModel.updateMyAppointment(courseId, false);
							}
							
							Ntro.modelStore().save(dashboardModel);
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
