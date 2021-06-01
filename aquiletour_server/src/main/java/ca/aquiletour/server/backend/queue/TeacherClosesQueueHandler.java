package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class TeacherClosesQueueHandler extends BackendMessageHandler<TeacherClosesQueueMessage> {
	
	@Override
	public void handleNow(ModelStoreSync modelStore, TeacherClosesQueueMessage message) throws BackendError {
		T.call(this);
		
		User teacher = (User) message.getUser();
		String courseId = message.getCourseId();
		
		SessionData sessionData = (SessionData) Ntro.currentSession().getSessionData();

		CourseListManager.closeQueueForUser(modelStore, CourseListModelTeacher.class, /* FIXME*/ null, message.getCourseId(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TeacherClosesQueueMessage message) throws BackendError {
		T.call(this);

		String courseId = message.getCourseId();
		
		QueueManager.closeQueue(modelStore, courseId);
	}
}
