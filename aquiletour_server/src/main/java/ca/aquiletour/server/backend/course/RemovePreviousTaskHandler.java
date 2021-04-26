package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.RemovePreviousTaskMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class RemovePreviousTaskHandler extends BackendMessageHandler<RemovePreviousTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RemovePreviousTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseUpdater.removePreviousTask(modelStore, courseId, message.getTaskToModify(), message.getTaskToRemove());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RemovePreviousTaskMessage message) {
		// TODO Auto-generated method stub
		
	}


}
