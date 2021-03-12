package ca.aquiletour.server.backend.dashboard;

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
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class DeleteCourseHandler extends BackendMessageHandler<DeleteCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		User fromUser = message.getUser();
		String courseId = message.getCourseId();
		
		UsersModel usersModel = modelStore.getModel(UsersModel.class, 
                "admin",
                "allUsers");
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				fromUser.getAuthToken(),
				courseId);
		
		if(usersModel != null) {
			
//			dashboardModel.deleteCourseById(courseId);
//			dashboardModel.save();
			Ntro.threadService().executeLater(new NtroTaskAsync() {
				
				@Override
				protected void onFailure(Exception e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				protected void runTaskAsync() {
					ArrayList<User> usersList = new ArrayList<User>();//TODO studentsId are in queueModel
					Map<String,User> usersMap = (Map<String, User>) usersModel.getUsers().getValue();;
					for (Map.Entry<String, User> entry : usersMap.entrySet()) {
						usersList.add(entry.getValue());
					}
					for (User user : usersList) {
						T.values(user.getId());
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
								user.getAuthToken(),
								user.getId());
						dashboardModel.deleteCourseById(courseId);
						dashboardModel.save();
						queueModel.deleteStudent(user.getId());
						queueModel.removeAllAppointmentsOfStudent(user.getId());
					}

					queueModel.save();
					QueuesModel allQueuesModel = modelStore.getModel(QueuesModel.class, fromUser.getAuthToken(), "allQueues");
					allQueuesModel.deleteQueue(courseId);
					allQueuesModel.save();
					QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, fromUser.getAuthToken(), "openQueues");
					openQueuesModel.deleteQueue(courseId);
					openQueuesModel.save();
					
				}
			});
		}else {
			
			// TODO: error handling
			
		}
	}
}
