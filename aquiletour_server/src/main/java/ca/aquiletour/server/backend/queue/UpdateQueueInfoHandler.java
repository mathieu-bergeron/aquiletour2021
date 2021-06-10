package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.messages.queue.UpdateQueueInfoMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class UpdateQueueInfoHandler extends BackendMessageHandler<UpdateQueueInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateQueueInfoMessage message) throws BackendError {
		T.call(this);
		
		QueueManager.updateQueueInfo(modelStore, 
				                     message.getSemesterId(),
				                     message.getCourseId(),
				                     message.getGroupId(),
				                     message.getQueueMessage(),
				                     message.getTeacherId());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateQueueInfoMessage message) throws BackendError {
		T.call(this);
		
	}

}
