package ca.aquiletour.server.backend;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.UsersModel;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class DeleteUserHandler extends BackendMessageHandler<DeleteUserMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, DeleteUserMessage message) {
		T.call(this);

		String userId = message.getUserId();

		UsersModel usersModel = modelStore.getModel(UsersModel.class, "admin", "allUsers");

		if (usersModel != null) {
			usersModel.getUsers().removeEntry(userId);;
			usersModel.save();

			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, "admin", userId);
					List<CourseSummary> courses = dashboardModel.getCourses().getValue();
					for (CourseSummary courseSummary : courses) {
						QueueModel queueModel = modelStore.getModel(QueueModel.class, "admin",
								courseSummary.getCourseId());
						queueModel.deleteStudent(userId);

						queueModel.save();
					}
					dashboardModel.emptyCourses();
					dashboardModel.save();
				}

				@Override
				protected void onFailure(Exception e) {
				}
			});

		} else {

			// TODO: error handling

		}
	}
}
