package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.RemoveNextTaskMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class RemoveNextTaskHandler extends BackendMessageHandler<RemoveNextTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RemoveNextTaskMessage message) throws BackendError {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseManager.removeNextTask(modelStore, courseId, message.getTaskToModify(), message.getTaskToRemove());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RemoveNextTaskMessage message) {
		// TODO Auto-generated method stub
		
	}


}
