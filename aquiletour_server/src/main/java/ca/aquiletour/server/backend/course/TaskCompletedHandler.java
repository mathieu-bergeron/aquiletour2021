package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.TaskCompletedMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class TaskCompletedHandler extends BackendMessageHandler<TaskCompletedMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, TaskCompletedMessage message) throws BackendMessageHandlerError {
		T.call(this);
		
		CourseManager.taskCompletedByUser(modelStore,
				                          message.coursePath(), 
				                          message.getTaskPath(), 
				                          message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TaskCompletedMessage message) {
		T.call(this);
		
	}

}
