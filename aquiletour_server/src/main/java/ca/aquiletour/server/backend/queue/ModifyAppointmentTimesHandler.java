package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimesMessage;
import ca.aquiletour.server.backend.queue_list.QueueListManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class ModifyAppointmentTimesHandler extends BackendMessageHandler<ModifyAppointmentTimesMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentTimesMessage message) throws BackendError {
		T.call(this);
		
		QueueManager.modifyAppointmentTimes(modelStore, message.getTimeIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentTimesMessage message) throws BackendError {
		T.call(this);

		QueueListManager.updateLastActivity(modelStore, message.getUser().getId());
	}

}
