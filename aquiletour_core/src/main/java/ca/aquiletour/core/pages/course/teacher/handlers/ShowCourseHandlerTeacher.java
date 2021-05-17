package ca.aquiletour.core.pages.course.teacher.handlers;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Course;
import ca.aquiletour.core.models.courses.teacher.CourseTeacher;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.teacher.messages.ShowTaskMessageTeacher;
import ca.ntro.core.system.trace.T;

public class ShowCourseHandlerTeacher extends ShowCourseHandler {

	@Override
	protected Class<? extends Course> modelClass() {
		T.call(this);
		
		return CourseTeacher.class;
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);
		
		return ShowTaskMessageTeacher.class;
	}

	@Override
	protected CoursePath coursePathFromMessage(ShowCourseMessage message) {
		T.call(this);
		
		return message.coursePath();
	}

}
