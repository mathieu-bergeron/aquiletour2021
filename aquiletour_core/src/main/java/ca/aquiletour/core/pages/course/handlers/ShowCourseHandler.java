package ca.aquiletour.core.pages.course.handlers;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowCourseHandler extends ControllerMessageHandler<CourseController,
                                                                CourseView,
                                                                ShowCourseMessage> {
	
	private Path currentCoursePath;

	@Override
	protected void handle(CourseController currentController, CourseView currentView, ShowCourseMessage message) {
		T.call(this);

		Path coursePath = message.coursePath();

		// XXX: change model only when needed
		if(!coursePath.equals(currentCoursePath)) {
			String authToken = currentController.context().user().getAuthToken();
			currentController.setModelLoader(CourseModel.class, authToken, coursePath);
			currentCoursePath = coursePath;

			ShowTaskMessage showTaskMessage = Ntro.messages().create(ShowTaskMessage.class);
			showTaskMessage.setTaskPath(message.getTaskPath());
			Ntro.messages().send(showTaskMessage);
		}

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showCourse(currentView);
	}
}
