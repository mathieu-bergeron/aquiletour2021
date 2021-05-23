package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class MoveAppointmentHandler extends BackendMessageHandler<MoveAppointmentMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore,MoveAppointmentMessage message) throws BackendError {
		T.call(this);
		
		String courseId = message.getCourseId();
		String appointmentId = message.getAppointmentId();
		String destinationId = message.getDestinationId();
		String beforeOrAfter = message.getBeforeOrAfter();
		
		QueueManager.moveAppointment(modelStore, courseId, appointmentId, destinationId, beforeOrAfter);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, MoveAppointmentMessage message) {
		T.call(this);
	}
}
