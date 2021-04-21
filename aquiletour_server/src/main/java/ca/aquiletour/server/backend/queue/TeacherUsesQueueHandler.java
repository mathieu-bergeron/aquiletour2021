package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);

		QueueUpdater.openQueue(modelStore, message.getCourseId());
	}
}
