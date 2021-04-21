package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteAppointmentHandler extends BackendMessageHandler<DeleteAppointmentMessage> {
	
	private Appointment deletedAppointment;

	@Override
	public void handleNow(ModelStoreSync modelStore,DeleteAppointmentMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		String appointmentId = message.getAppointmentId();
		
		deletedAppointment = QueueUpdater.getAppointmentById(modelStore, courseId, appointmentId);

		QueueUpdater.deleteAppointment(modelStore, courseId, appointmentId);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteAppointmentMessage message) {
		T.call(this);

		QueueUpdater.deleteAppointmentUpdates(modelStore, message.getCourseId(), deletedAppointment);
	}
}
