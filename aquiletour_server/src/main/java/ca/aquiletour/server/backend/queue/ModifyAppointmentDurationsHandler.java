package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurations;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentDurationsHandler extends BackendMessageHandler<ModifyAppointmentDurations> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentDurations message) throws BackendMessageHandlerError {
		T.call(this);

		QueueUpdater.modifyAppointmentDurations(modelStore, message.getDurationIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentDurations message) {
		T.call(this);
		
	}

}
