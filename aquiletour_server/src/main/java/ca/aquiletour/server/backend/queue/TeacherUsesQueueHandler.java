package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, TeacherUsesQueueMessage message) throws BackendError {
		T.call(this);
		
		CourseListManager.openQueueForUser(modelStore, 
				                           CourseListModelTeacher.class,
				                           message.getSemesterId(),
				                           message.getCourseId(), 
				                           message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);

		//QueueUpdater.openQueue(modelStore, message.getCourseId());
	}
}
