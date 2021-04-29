package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddNextTaskHandler extends BackendMessageHandler<AddNextTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddNextTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();
		
		CourseUpdater.addNextTask(modelStore, CourseModel.class, courseId, message.getPreviousPath(), message.getNextTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddNextTaskMessage message) {
	}


}
