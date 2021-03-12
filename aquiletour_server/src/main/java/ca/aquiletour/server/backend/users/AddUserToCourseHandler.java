package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class AddUserToCourseHandler extends BackendMessageHandler<AddUserToCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddUserToCourseMessage message) {
		T.call(this);
		
		String userId = message.getUserId();
		String courseId = message.getCourseId();
		
		DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
				                                            "admin",
				                                            userId);
		
		if(dashboardModel != null) {
			
			CourseSummary newCourse = new CourseSummary();
			newCourse.setTitle(courseId);
			newCourse.setCourseId(courseId);
			dashboardModel.addCourse(newCourse);
			dashboardModel.setTeacherAvailability(false, courseId);
			dashboardModel.updateMyAppointment(courseId, false);
			dashboardModel.save();
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					QueueModel queueModel = modelStore.getModel(QueueModel.class, 
													   "admin",
													   courseId);
					queueModel.addStudentToClass(userId);

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
