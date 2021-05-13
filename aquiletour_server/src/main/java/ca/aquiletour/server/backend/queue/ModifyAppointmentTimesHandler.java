package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimes;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentTimesHandler extends BackendMessageHandler<ModifyAppointmentTimes> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentTimes message) throws BackendMessageHandlerError {
		T.call(this);
		
		QueueManager.modifyAppointmentTimes(modelStore, message.getTimeIncrementSeconds(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentTimes message) {
		T.call(this);
		
	}

}
