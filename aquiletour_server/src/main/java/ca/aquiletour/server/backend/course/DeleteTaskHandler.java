package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteTaskHandler extends BackendMessageHandler<DeleteTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseUpdater.deleteTask(modelStore, courseId, message.getTaskToDelete());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteTaskMessage message) {
		// TODO Auto-generated method stub
		
	}

}
