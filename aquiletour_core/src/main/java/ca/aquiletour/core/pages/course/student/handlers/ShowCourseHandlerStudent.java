package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.messages.ShowTaskMessageStudent;
import ca.ntro.core.system.trace.T;

public class ShowCourseHandlerStudent extends ShowCourseHandler {

	@Override
	protected Class<? extends CourseModelBase> modelClass() {
		T.call(this);

		//return CourseModelStudent.class;
		
		// XXX: always use teacher model
		return CourseModel.class;
	}

	@Override
	protected Class<? extends ShowTaskMessage> showTaskMessageClass() {
		T.call(this);
		
		return ShowTaskMessageStudent.class;
	}
}
