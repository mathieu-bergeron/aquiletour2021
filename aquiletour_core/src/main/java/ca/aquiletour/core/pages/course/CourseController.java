package ca.aquiletour.core.pages.course;

import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CourseController extends NtroController<RootController>{

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(CourseView.class, "fr");

		setModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(ShowCourseMessage.class, new ShowCourseHandler());

		addSubViewLoader(TaskView.class, context.lang());

		addModelViewSubViewMessageHandler(TaskView.class, ShowTaskMessage.class, new CourseViewModel());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
