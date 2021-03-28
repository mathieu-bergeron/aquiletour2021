package ca.aquiletour.core.pages.course.teacher;

import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.teacher.handlers.CourseViewModelTeacher;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.core.pages.course.teacher.views.TaskViewTeacher;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class CourseControllerTeacher extends CourseController {

	@Override
	protected Class<? extends CourseView> viewClass() {
		T.call(this);

		return CourseViewTeacher.class;
	}

	@Override
	protected Class<? extends TaskView> subViewClass() {
		T.call(this);

		return TaskViewTeacher.class;
	}

	@Override
	protected ControllerMessageHandler<?, ?, ?> showHandler() {
		T.call(this);

		return new ShowCourseHandler();
	}

	@Override
	protected ModelViewSubViewMessageHandler<?, ?, ?> viewModel() {
		T.call(this);

		return new CourseViewModelTeacher();
	}
}
