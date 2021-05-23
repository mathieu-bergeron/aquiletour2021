package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddPreviousTaskHandler extends BackendMessageHandler<AddPreviousTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddPreviousTaskMessage message) throws BackendError {
		T.call(this);

		CourseManager.addPreviousTask(modelStore, message.coursePath(), message.getNextPath(), message.getPreviousTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddPreviousTaskMessage message) throws BackendError {
		T.call(this);

		CourseManager.addPreviousTaskForStudents(modelStore, message.coursePath(), message.getNextPath(), message.getPreviousTask());
	}

}
