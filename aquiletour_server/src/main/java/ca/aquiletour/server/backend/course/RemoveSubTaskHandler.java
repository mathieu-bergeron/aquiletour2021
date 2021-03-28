package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class RemoveSubTaskHandler extends BackendMessageHandler<RemoveSubTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RemoveSubTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseUpdater.removeSubTask(modelStore, courseId, message.getTaskToModify(), message.getTaskToRemove());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RemoveSubTaskMessage message) {
		// TODO Auto-generated method stub
		
	}


}