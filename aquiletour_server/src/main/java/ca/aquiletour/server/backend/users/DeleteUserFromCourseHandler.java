package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserFromCourseMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class DeleteUserFromCourseHandler extends BackendMessageHandler<DeleteUserFromCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, DeleteUserFromCourseMessage message) {
		T.call(this);
		
		String userId = message.getUserId();
		String courseId = message.getCourseId();
		
		DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
				                                            "admin",
				                                            userId);
		
		if(dashboardModel != null) {
			
			dashboardModel.deleteCourseById(courseId);;
			dashboardModel.save();
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					QueueModel queueModel = modelStore.getModel(QueueModel.class, 
													   "admin",
													   courseId);
					queueModel.removeStudentFromClass(userId);
					queueModel.removeAllAppointmentsOfStudent(userId);

					queueModel.save();
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
