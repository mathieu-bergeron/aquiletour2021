package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.stores.LocalStore;

public class AddAppointmentHandler extends BackendMessageHandler<AddAppointmentMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {
			
			queueModel.addAppointment(message.getAppointment());
			LocalStore.save(queueModel);
			
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
								dashboardModel.updateMyAppointment(courseId, true);
							}
							LocalStore.save(dashboardModel);
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
