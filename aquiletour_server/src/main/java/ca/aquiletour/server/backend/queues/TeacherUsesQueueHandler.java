package ca.aquiletour.server.backend.queues;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);

		User teacher = message.getUser();
		String courseId = message.getCourseId();
		
		Ntro.threadService().executeLater(new TeacherUsesQueueBackgroundTask(teacher, courseId));
	}
}
