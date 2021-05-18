package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurationsMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentDurationsHandler extends BackendMessageHandler<ModifyAppointmentDurationsMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentDurationsMessage message) throws BackendMessageHandlerError {
		T.call(this);

		QueueManager.modifyAppointmentDurations(modelStore, message.getDurationIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentDurationsMessage message) {
		T.call(this);
		
	}

}
