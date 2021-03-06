package ca.aquiletour.core.pages.course;

import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class CourseController extends NtroController<RootController>{

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(viewClass(), "fr");

		setModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(showMessageClass(), showHandler());

		addSubViewLoader(subViewClass(), context.lang());

		addModelViewSubViewMessageHandler(subViewClass(), showTaskMessageClass(), viewModel());
	}

	protected abstract Class<? extends CourseView> viewClass();
	protected abstract Class<? extends TaskView> subViewClass();
	protected abstract Class<? extends ShowCourseMessage> showMessageClass();
	protected abstract ControllerMessageHandler<?,?,?> showHandler();
	protected abstract Class<? extends ShowTaskMessage> showTaskMessageClass();
	protected abstract ModelViewSubViewMessageHandler<?,?,?> viewModel();

	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}
}
