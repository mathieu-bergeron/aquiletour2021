package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.ntro.core.system.trace.T;

public class ShowCourseHandlerStudent extends ShowCourseHandler {

	@Override
	protected Class<? extends CourseModel> modelClass() {
		T.call(this);

		return CourseModelStudent.class;
	}
}
