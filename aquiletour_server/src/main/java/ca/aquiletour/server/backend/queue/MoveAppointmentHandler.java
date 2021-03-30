package ca.aquiletour.server.backend.queue;


<<<<<<< HEAD
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentDestination;
=======
>>>>>>> main
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class MoveAppointmentHandler extends BackendMessageHandler<MoveAppointmentMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore,MoveAppointmentMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		String appointmentId = message.getAppointmentId();
<<<<<<< HEAD
		MoveAppointmentDestination destination = message.getDestination();
		
		QueueUpdater.moveAppointment(modelStore, courseId, appointmentId, destination);
=======
		String destinationId = message.getDestinationId();
		String beforeOrAfter = message.getBeforeOrAfter();
		
		QueueUpdater.moveAppointment(modelStore, courseId, appointmentId, destinationId, beforeOrAfter);
>>>>>>> main
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, MoveAppointmentMessage message) {
		T.call(this);
	}
}
