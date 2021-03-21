package ca.aquiletour.server.backend.dashboard;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.server.backend.queue.QueueModels;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteCourseHandler extends BackendMessageHandler<DeleteCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();
		String courseId = message.getCourseId();

		DashboardModels.deleteQueueForUser(modelStore, courseId, teacher.getId());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		QueueModels.deleteQueue(modelStore, message.getCourseId());
	}
}
