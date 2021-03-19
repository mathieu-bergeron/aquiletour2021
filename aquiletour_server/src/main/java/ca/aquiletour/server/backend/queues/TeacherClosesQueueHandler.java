package ca.aquiletour.server.backend.queues;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class TeacherClosesQueueHandler extends BackendMessageHandler<TeacherClosesQueueMessage> {
	
	@Override
	public void handle(ModelStoreSync modelStore, TeacherClosesQueueMessage message) {
		T.call(this);
		
		User teacher = (User) message.getUser();
		String courseId = message.getCourseId();

		Ntro.threadService().executeLater(new TeacherClosesQueueBackgroundTask(teacher, courseId));
	}
}
