package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimesMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentTimesHandler extends BackendMessageHandler<ModifyAppointmentTimesMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentTimesMessage message) throws BackendMessageHandlerError {
		T.call(this);
		
		QueueManager.modifyAppointmentTimes(modelStore, message.getTimeIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentTimesMessage message) {
		T.call(this);
		
	}

}
