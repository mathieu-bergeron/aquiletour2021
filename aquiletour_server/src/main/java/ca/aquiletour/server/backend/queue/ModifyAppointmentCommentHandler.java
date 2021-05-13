package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.pages.queue.student.messages.ModifyAppointmentComment;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ModifyAppointmentCommentHandler extends BackendMessageHandler<ModifyAppointmentComment>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ModifyAppointmentComment message) throws BackendMessageHandlerError {
		T.call(this);
		
		T.here();
		
		QueueManager.modifyAppointmentComment(modelStore, message.getQueueId(), message.getComment(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ModifyAppointmentComment message) {
		T.call(this);
		
	}

}
