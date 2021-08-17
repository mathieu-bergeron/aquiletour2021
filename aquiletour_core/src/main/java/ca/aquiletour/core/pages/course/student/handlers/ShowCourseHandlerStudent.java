package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.CoursePathStudent;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.messages.ShowTaskMessageStudent;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowCourseHandlerStudent extends ShowCourseHandler {

	@Override
	protected Class<? extends CourseModel<?>> modelClass() {
		T.call(this);

		return CourseModelStudent.class;
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);
		
		return ShowTaskMessageStudent.class;
	}

	@Override
	protected CoursePath coursePathFromMessage(ShowCourseMessage message) {
		T.call(this);
		
		return CoursePathStudent.fromCoursePath(message.coursePath(), ((User)Ntro.currentUser()).getId());
	}
}
