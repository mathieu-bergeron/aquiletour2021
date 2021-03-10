package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class AddUserHandler extends BackendMessageHandler<AddUserMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddUserMessage message) {
		T.call(this);
		
		User user = message.getUser();
		
		DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
				                                            user.getAuthToken(),
				                                            user.getId());
		
		if(dashboardModel != null) {		
			
			dashboardModel.save();
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					UsersModel usersModel = modelStore.getModel(UsersModel.class, "admin", "allUsers");
					usersModel.addUser(user);
					usersModel.save();
				}

				@Override
				protected void onFailure(Exception e) {
				}
			});

		}else {
			
			// TODO: error handling
			
		}
	}
}
