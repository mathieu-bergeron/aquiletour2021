package ca.aquiletour.core.pages.course.handlers;

import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowCourseHandler extends ControllerMessageHandler<CourseController,
                                                                CourseView,
                                                                ShowCourseMessage> {
	
	private String currentCourseId;

	@Override
	protected void handle(CourseController currentController, CourseView currentView, ShowCourseMessage message) {
		T.call(this);

		String courseId = message.getCourseId();
		
		MustNot.beNull(courseId);
		
		if(!courseId.equals(currentCourseId)) {
			// XXX: change model only when needed
			String authToken = currentController.context().user().getAuthToken();
			currentController.setModelLoader(CourseModel.class, authToken, courseId);
			currentCourseId = courseId;
			
		}

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showCourse(currentView);
	}
}
