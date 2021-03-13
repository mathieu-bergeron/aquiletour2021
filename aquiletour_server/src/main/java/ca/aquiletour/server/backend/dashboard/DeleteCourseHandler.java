package ca.aquiletour.server.backend.dashboard;

import java.util.ArrayList;
import java.util.Map;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.services.Ntro;

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
					ArrayList<User> usersList = new ArrayList<User>();
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
						Ntro.modelStore().save(dashboardModel);
						queueModel.deleteStudent(user.getId());
						queueModel.removeAllAppointmentsOfStudent(user.getId());
					}

					modelStore.save(queueModel);

					QueuesModel allQueuesModel = modelStore.getModel(QueuesModel.class, fromUser.getAuthToken(), "allQueues");
					allQueuesModel.deleteQueue(courseId);

					modelStore.save(allQueuesModel);
					QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, fromUser.getAuthToken(), "openQueues");

					openQueuesModel.deleteQueue(courseId);
					modelStore.save(openQueuesModel);
					
				}
			});
		}else {
			
			// TODO: error handling
			
		}
	}
}
