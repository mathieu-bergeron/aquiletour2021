package ca.aquiletour.core.backend.handlers;

import ca.aquiletour.core.backend.DashboardBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class DeleteCourseHandler extends MessageHandler<DashboardBackendController,DeleteCourseMessage> {

	@Override
	protected void handle(DeleteCourseMessage message) {
		T.call(this);
		
		User fromUser = message.getUser();
		String courseId = message.getCourseId();
		
		DashboardModel dashboardModel = getController().getModel(DashboardModel.class, 
				                                                 fromUser.getAuthToken(),
				                                                 fromUser.getId());
		
		if(dashboardModel != null) {
			
			dashboardModel.deleteCourseById(courseId);
			dashboardModel.save();

//			// XXX: create Queue
//			QueueModel queueModel = getController().getModel(QueueModel.class, 
//													fromUser.getAuthToken(),
//													courseId);
//
//			queueModel.save();//TODO what to do with queueModel

		}else {
			
			// TODO: error handling
			
		}
	}
}
