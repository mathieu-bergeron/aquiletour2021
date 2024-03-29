package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class RemoveSubTaskHandler extends BackendMessageHandler<RemoveSubTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RemoveSubTaskMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.removeSubTask(modelStore, message.coursePath(), message.getTaskToModify(), message.getTaskToRemove());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RemoveSubTaskMessage message) {
		// TODO Auto-generated method stub
		
	}


}
