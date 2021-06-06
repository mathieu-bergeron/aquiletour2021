package ca.aquiletour.server.backend.time;

import ca.aquiletour.core.messages.time.TimePassesMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class TimePassesHandler extends BackendMessageHandler<TimePassesMessage> {
	
	private long secondsElapsedSinceLastReset = 0;

	@Override
	public void handleNow(ModelStoreSync modelStore, TimePassesMessage message) throws BackendError {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TimePassesMessage message) {
		T.call(this);

		secondsElapsedSinceLastReset += message.getElapsedTimeSeconds();
		
		// do different things at different intervals
		// e.g. recompute late tasks every 10 minutes
		//      update queues currentTime every minute?
		
		
	}

}
