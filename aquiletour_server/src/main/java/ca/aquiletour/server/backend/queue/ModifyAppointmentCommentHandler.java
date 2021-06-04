package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.student.messages.ModifyAppointmentCommentMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentCommentHandler extends BackendMessageHandler<ModifyAppointmentCommentMessage>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentCommentMessage message) throws BackendError {
		T.call(this);
		
		QueueManager.modifyAppointmentComment(modelStore, message.getQueueId(), message.getComment(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentCommentMessage message) {
		T.call(this);
		
	}

}
