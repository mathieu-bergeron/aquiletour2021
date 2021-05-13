package ca.aquiletour.server.backend.dashboard;

import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.dashboard.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteCourseHandler extends BackendMessageHandler<DeleteCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();
		String courseId = message.getCourseId();
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		QueueManager.deleteQueue(modelStore, message.getCourseId());
	}
}
