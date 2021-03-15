package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

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
			
			modelStore.save(dashboardModel);
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					QueueModel queueModel = modelStore.getModel(QueueModel.class, 
													   "admin",
													   courseId);
					queueModel.addStudentToClass(userId);

					modelStore.save(queueModel);
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