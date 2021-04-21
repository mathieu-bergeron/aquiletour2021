package ca.aquiletour.core.pages.course.teacher.handlers;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.pages.course.handlers.ShowCourseHandler;
import ca.ntro.core.system.trace.T;

public class ShowCourseHandlerTeacher extends ShowCourseHandler {

	@Override
	protected Class<? extends CourseModel> modelClass() {
		T.call(this);
		
		return CourseModelTeacher.class;
	}

}
