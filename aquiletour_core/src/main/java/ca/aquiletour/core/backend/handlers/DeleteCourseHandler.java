package ca.aquiletour.core.backend.handlers;

import java.util.ArrayList;
import java.util.Map;

import ca.aquiletour.core.backend.DashboardBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class DeleteCourseHandler extends MessageHandler<DashboardBackendController,DeleteCourseMessage> {

	@Override
	protected void handle(DeleteCourseMessage message) {
		T.call(this);
		
		User fromUser = message.getUser();
		String courseId = message.getCourseId();
		
		UsersModel usersModel = getController().getModel(UsersModel.class, 
                "admin",
                "allUsers");
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				fromUser.getAuthToken(),
				courseId);
		
		if(usersModel != null) {
			
//			dashboardModel.deleteCourseById(courseId);
//			dashboardModel.save();
			T.here();
			ArrayList<User> usersList = new ArrayList<User>();
			Map<String,User> usersMap = (Map<String, User>) usersModel.getUsers().getValue();;
			for (Map.Entry<String, User> entry : usersMap.entrySet()) {
				usersList.add(entry.getValue());
			}
			for (User user : usersList) {
				T.values(user.getId());
				DashboardModel dashboardModel = getController().getModel(DashboardModel.class, 
						user.getAuthToken(),
						user.getId());
				dashboardModel.deleteCourseById(courseId);
				dashboardModel.save();
				queueModel.deleteStudent(user.getId());
			}

			queueModel.save();
			QueuesModel queuesModel = getController().getModel(QueuesModel.class, fromUser.getAuthToken(), "allQueues");
			queuesModel.deleteQueue(courseId);
			queuesModel.save();

		}else {
			
			// TODO: error handling
			
		}
	}
}
