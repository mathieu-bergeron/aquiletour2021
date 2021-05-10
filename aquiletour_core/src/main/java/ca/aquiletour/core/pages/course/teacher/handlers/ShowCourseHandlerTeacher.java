package ca.aquiletour.core.pages.course.teacher.handlers;

import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.teacher.messages.ShowTaskMessageTeacher;
import ca.ntro.core.system.trace.T;

public class ShowCourseHandlerTeacher extends ShowCourseHandler {

	@Override
	protected Class<? extends CourseModelBase> modelClass() {
		T.call(this);
		
		return CourseModel.class;
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);
		
		return ShowTaskMessageTeacher.class;
	}

}
