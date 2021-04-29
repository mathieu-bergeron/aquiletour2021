package ca.aquiletour.core.pages.course.teacher;

import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.teacher.handlers.CourseViewModelTeacher;
import ca.aquiletour.core.pages.course.teacher.handlers.ShowCourseHandlerTeacher;
import ca.aquiletour.core.pages.course.teacher.messages.ShowCourseMessageTeacher;
import ca.aquiletour.core.pages.course.teacher.messages.ShowTaskMessageTeacher;
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
	protected Class<? extends ShowCourseMessage> showMessageClass() {
		T.call(this);
		
		return ShowCourseMessageTeacher.class;
	}

	@Override
	protected ControllerMessageHandler<?, ?, ?> showHandler() {
		T.call(this);

		return new ShowCourseHandlerTeacher();
	}

	@Override
	protected ModelViewSubViewMessageHandler<?, ?, ?> viewModel() {
		T.call(this);

		return new CourseViewModelTeacher();
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);

		return ShowTaskMessageTeacher.class;
	}

}
