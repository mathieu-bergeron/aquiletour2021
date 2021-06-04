package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AtomicTaskCompletedMessage;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AtomicTaskCompletedHandler extends BackendMessageHandler<AtomicTaskCompletedMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AtomicTaskCompletedMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.updateStudentTaskCompletions(modelStore,
				                                   message.coursePath(), 
				                                   message.getTaskPath(), 
				                                   message.getAtomicTaskId(),
				                                   message.getCompletion(),
				                                   message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AtomicTaskCompletedMessage message) throws BackendError {
		T.call(this);

		CourseManager.updateCourseTaskCompletions(modelStore,
				                                  message.coursePath(), 
				                                  message.getTaskPath(), 
				                                  message.getAtomicTaskId(),
				                                  message.getCompletion(),
				                                  message.getUser());

		DashboardManager.updateCurrentTasks(modelStore, message.coursePath());

		CourseManager.updateCourseLog(modelStore,
				                      message.coursePath(), 
				                      message.getUser(),
				                      message.getTaskPath(), 
				                      message.getGroupId(),
				                      message.getAtomicTaskId(),
				                      message.getCompletion());
	}
}
