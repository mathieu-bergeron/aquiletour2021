package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurationsMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class ModifyAppointmentDurationsHandler extends BackendMessageHandler<ModifyAppointmentDurationsMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentDurationsMessage message) throws BackendError {
		T.call(this);

		QueueManager.modifyAppointmentDurations(modelStore, message.getDurationIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentDurationsMessage message) {
		T.call(this);
		
	}

}
