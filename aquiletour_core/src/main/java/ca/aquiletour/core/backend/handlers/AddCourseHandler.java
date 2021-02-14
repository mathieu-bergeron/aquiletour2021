package ca.aquiletour.core.backend.handlers;

import ca.aquiletour.core.backend.DashboardBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class AddCourseHandler extends MessageHandler<DashboardBackendController,AddCourseMessage> {

	@Override
	protected void handle(AddCourseMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourse().getTitle();
		
		DashboardModel dashboardModel = getController().getModel(DashboardModel.class, 
				                                                 requestingUser.getAuthToken(),
				                                                 courseId);
		
		dashboardModel.addCourse(message.getCourse());
		dashboardModel.save();

		// XXX: create Queue
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				                                requestingUser.getAuthToken(),
				                                courseId);

		queueModel.save();
	}
}
