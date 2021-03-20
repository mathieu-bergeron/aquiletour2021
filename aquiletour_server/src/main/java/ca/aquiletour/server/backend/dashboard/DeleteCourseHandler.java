package ca.aquiletour.server.backend.dashboard;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class DeleteCourseHandler extends BackendMessageHandler<DeleteCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, DeleteCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();
		String courseId = message.getCourseId();
		
		modelStore.updateModel(DashboardModel.class, 
							   teacher.getAuthToken(), 
							   teacher.getId(), 
							   new ModelUpdater<DashboardModel>() {

								@Override
								public void update(DashboardModel teacherDashboard) {
									T.call(this);

									teacherDashboard.deleteCourseById(courseId);
								}
							});
		
		Ntro.threadService().executeLater(new DeleteCourseBackgroundTask(teacher, courseId));
	}
}
