package ca.aquiletour.server.backend.dashboard;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.server.backend.queue.QueueModels;
import ca.aquiletour.server.backend.queues.QueuesModels;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();

		DashboardModels.addQueueForUser(modelStore, message.getCourse(), teacher);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);

		User teacher = message.getUser();
		String courseId = message.getCourse().getCourseId();

		QueueModels.createQueue(modelStore, courseId, teacher);
		QueuesModels.createQueue(modelStore, courseId, teacher);
	}
}
