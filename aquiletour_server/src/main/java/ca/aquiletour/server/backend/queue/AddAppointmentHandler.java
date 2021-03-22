package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddAppointmentHandler extends BackendMessageHandler<AddAppointmentMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddAppointmentMessage message) {
		T.call(this);

		User student = message.getUser();
		String courseId = message.getCourseId();
		
		QueueUpdater.addAppointmentForUser(modelStore, courseId, student);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddAppointmentMessage message) {
		T.call(this);

		QueueUpdater.addAppointmentUpdates(modelStore, message.getCourseId());
	}
}
