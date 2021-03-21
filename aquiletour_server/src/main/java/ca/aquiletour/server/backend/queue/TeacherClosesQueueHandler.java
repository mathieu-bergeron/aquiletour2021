package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.server.backend.dashboard.DashboardModels;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class TeacherClosesQueueHandler extends BackendMessageHandler<TeacherClosesQueueMessage> {
	
	@Override
	public void handleNow(ModelStoreSync modelStore, TeacherClosesQueueMessage message) {
		T.call(this);
		
		User teacher = (User) message.getUser();
		String courseId = message.getCourseId();
		
		DashboardModels.closeQueueForUser(modelStore, courseId, teacher.getId());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TeacherClosesQueueMessage message) {
		T.call(this);

		String courseId = message.getCourseId();
		
		QueueModels.closeQueue(modelStore, courseId);
	}
}
