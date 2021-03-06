package ca.aquiletour.core.pages.course.student;

import ca.aquiletour.core.pages.course.CourseController;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.handlers.CourseViewModelStudent;
import ca.aquiletour.core.pages.course.student.handlers.ShowCourseHandlerStudent;
import ca.aquiletour.core.pages.course.student.messages.ShowCourseMessageStudent;
import ca.aquiletour.core.pages.course.student.messages.ShowTaskMessageStudent;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.core.pages.course.student.views.TaskViewStudent;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class CourseControllerStudent extends CourseController {

	@Override
	protected Class<? extends CourseView> viewClass() {
		T.call(this);

		return CourseViewStudent.class;
	}

	@Override
	protected Class<? extends TaskView> subViewClass() {
		T.call(this);

		return TaskViewStudent.class;
	}

	@Override
	protected Class<? extends ShowCourseMessage> showMessageClass() {
		T.call(this);
		
		return ShowCourseMessageStudent.class;
	}

	@Override
	protected ControllerMessageHandler<?, ?, ?> showHandler() {
		T.call(this);

		return new ShowCourseHandlerStudent();
	}

	@Override
	protected ModelViewSubViewMessageHandler<?, ?, ?> viewModel() {
		T.call(this);

		return new CourseViewModelStudent();
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);
		
		return ShowTaskMessageStudent.class;
	}

}
