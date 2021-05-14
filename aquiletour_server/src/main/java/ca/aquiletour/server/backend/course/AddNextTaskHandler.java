package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddNextTaskHandler extends BackendMessageHandler<AddNextTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddNextTaskMessage message) {
		T.call(this);
		
		CourseManager.addNextTask(modelStore, message.coursePath(), message.getPreviousPath(), message.getNextTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddNextTaskMessage message) {
	}


}
