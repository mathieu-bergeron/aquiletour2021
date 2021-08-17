package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.server.backend.log.LogManagerQueue;
import ca.aquiletour.server.backend.queue_list.QueueListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class DeleteAppointmentHandler extends BackendMessageHandler<DeleteAppointmentMessage> {
	
	@Override
	public void handleNow(ModelStoreSync modelStore,DeleteAppointmentMessage message) throws BackendError {
		T.call(this);
		
		String courseId = message.getCourseId();
		String appointmentId = message.getAppointmentId();
		
		QueueManager.deleteAppointment(modelStore, courseId, appointmentId);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteAppointmentMessage message) throws BackendError {
		T.call(this);
		
		String queueId = message.getCourseId();
		String appointmentId = message.getAppointmentId();

		LogManagerQueue.logDeleteAppointment(modelStore, queueId, appointmentId, message.getUser());

		QueueListManager.updateLastActivity(modelStore, queueId);
	}
}
