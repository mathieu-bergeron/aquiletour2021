package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class AddNextTaskHandler extends BackendMessageHandler<AddNextTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddNextTaskMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.addNextTask(modelStore, message.coursePath(), message.getPreviousPath(), message.getNextTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddNextTaskMessage message) throws BackendError {
		T.call(this);

		CourseManager.addNextTaskForStudents(modelStore, message.coursePath(), message.getPreviousPath(), message.getNextTask());
	}
}
