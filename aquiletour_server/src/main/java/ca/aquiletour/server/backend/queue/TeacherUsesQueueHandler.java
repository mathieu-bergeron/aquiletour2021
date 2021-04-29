package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);
		
		SessionData sessionData = (SessionData) Ntro.currentSession().getSessionData();
		
		CourseListUpdater.openQueueForUser(modelStore, 
				                           CourseListModelTeacher.class,
				                           sessionData.getCurrentSemester(), 
				                           message.getCourseId(), 
				                           message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);

		//QueueUpdater.openQueue(modelStore, message.getCourseId());
	}
}
