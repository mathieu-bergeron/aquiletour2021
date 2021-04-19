package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddTaskHandler extends BackendMessageHandler<AddSubTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSubTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseUpdater.addSubTask(modelStore, courseId, message.getParentPath(), message.getSubTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSubTaskMessage message) {
		T.call(this);
	}
}
