package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class AddAppointmentHandler extends BackendMessageHandler<AddAppointmentMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddAppointmentMessage message) {
		T.call(this);

		User student = message.getUser();
		String courseId = message.getCourseId();

		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				"admin",
				courseId);

		DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
				student.getAuthToken(),
				student.getId());

		if(queueModel != null 
				&& !dashboardModel.doesStudentAlreadyHaveAppointment(courseId) 
				&& !(queueModel.getTeacherId().equals(student.getId()))) {
			
			Appointment appointment = new Appointment();
			appointment.setStudentId(student.getId());
			appointment.setStudentName(student.getName());
			appointment.setStudentSurname(student.getSurname());
			queueModel.addAppointment(appointment);
			modelStore.save(queueModel);

			dashboardModel.updateMyAppointment(courseId, true);
			modelStore.save(dashboardModel);
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					// XXX: must get a fresh copy of the modelStore (it is thread specific)
					ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());

					int nbAppointment = queueModel.getAppointments().size();

					DashboardModel teacherDashboard = modelStore.getModel(DashboardModel.class, "admin", queueModel.getTeacherId());
					teacherDashboard.updateNbAppointmentOfCourse(courseId, nbAppointment);
					modelStore.save(teacherDashboard);

					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", studentId);

						if(dashboardModel != null) {
							dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
							dashboardModel.updateMyAppointment(courseId, true);
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
