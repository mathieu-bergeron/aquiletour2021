package ca.aquiletour.core.pages.dashboards.teacher.messages;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.system.trace.T;

public class AddCourseHandler extends ModelMessageHandler<DashboardModel, AddCourseMessage> {

	@Override
	protected void handle(DashboardModel model, AddCourseMessage message) {
		T.call(this);
		
		model.addCourse(message.getCourse());
		model.save();
	}
}
