package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.messages.queue.UpdateIsQueueOpenMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class UpdateIsQueueOpenHandler extends BackendMessageHandler<UpdateIsQueueOpenMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateIsQueueOpenMessage message) throws BackendError {
		T.call(this);
		
		QueueManager.updateIsQueueOpen(modelStore, 
				                       message.getCourseId(),
				                       message.getIsQueueOpen(),
				                       message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateIsQueueOpenMessage message) throws BackendError {
		T.call(this);
		
	}

}
