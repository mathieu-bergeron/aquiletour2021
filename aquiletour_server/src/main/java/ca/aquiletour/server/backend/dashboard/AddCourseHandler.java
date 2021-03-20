package ca.aquiletour.server.backend.dashboard;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();
		String courseId = message.getCourse().getCourseId();
		
		modelStore.updateModel(DashboardModel.class, 
							   teacher.getAuthToken(), 
							   teacher.getId(), 
							   new ModelUpdater<DashboardModel>() {

								@Override
								public void update(DashboardModel teacherDashboard) {
									T.call(this);

									teacherDashboard.addCourse(message.getCourse());
								}
							});

		Ntro.threadService().executeLater(new AddCourseBackgroundTask(teacher, courseId));
	}
}
