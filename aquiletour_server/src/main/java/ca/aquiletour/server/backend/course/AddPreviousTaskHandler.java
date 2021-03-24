package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddPreviousTaskHandler extends BackendMessageHandler<AddPreviousTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddPreviousTaskMessage message) {
		T.call(this);
		
		T.here();

		String courseId = message.getCourseId();

		CourseUpdater.addPreviousTask(modelStore, courseId, message.getNextPath(), message.getPreviousTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddPreviousTaskMessage message) {
		// TODO Auto-generated method stub
		
	}

}
