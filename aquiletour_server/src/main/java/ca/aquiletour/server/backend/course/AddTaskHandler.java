package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.AddTaskMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.aquiletour.server.backend.queue.QueueUpdater;
import ca.aquiletour.server.backend.queues.QueuesUpdater;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddTaskHandler extends BackendMessageHandler<AddTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddTaskMessage message) {
		T.call(this);
		
		String courseId = message.getCourseId();

		CourseUpdater.addTaskToCourse(modelStore, courseId, message.getTask());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddTaskMessage message) {
		T.call(this);
	}
}
