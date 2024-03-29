package ca.aquiletour.server.backend.queue;


import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.aquiletour.server.backend.log.LogManagerQueue;
import ca.aquiletour.server.backend.queue_list.QueueListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class AddAppointmentHandler extends BackendMessageHandler<AddAppointmentMessage> {
	
	private Appointment addedAppointment = null;

	@Override
	public void handleNow(ModelStoreSync modelStore, AddAppointmentMessage message) throws BackendError {
		T.call(this);

		User user = message.getUser();
		String queueId = message.getQueueId();
		
		if(user instanceof Guest || user instanceof TeacherGuest || user instanceof StudentGuest) {
			
			List<NtroMessage> delayedMessages = new ArrayList<>();
			delayedMessages.add(message);

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setMessageToUser("SVP se connecter pour prendre rendez-vous");
			showLoginMenuMessage.setDelayedMessages(delayedMessages);
			Ntro.messages().send(showLoginMenuMessage);

		}else {

			addedAppointment = QueueManager.addAppointmentForUser(modelStore, 
					                           queueId, 
					                           message.getCoursePath(),
					                           message.getTaskPath(),
					                           message.getTaskTitle(),
					                           user);
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddAppointmentMessage message) throws BackendError {
		T.call(this);

		User user = message.getUser();
		String queueId = message.getQueueId();
		
		if(addedAppointment != null) {

			LogManagerQueue.logNewAppointment(modelStore, queueId, user, Ntro.calendar().now(), addedAppointment);
			QueueListManager.updateNumberOfAppointments(modelStore, queueId, Long.valueOf(addedAppointment.getId()));
			QueueListManager.updateLastActivity(modelStore, queueId);
		}
	}
}
