package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class DeleteTaskHandler extends BackendMessageHandler<DeleteTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteTaskMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.deleteTask(modelStore, message.coursePath(), message.getTaskToDelete());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteTaskMessage message) {
		// TODO Auto-generated method stub
		
	}

}
