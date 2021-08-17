package ca.aquiletour.core.pages.course_list.teacher.handlers;

import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.system.trace.T;

public class ShowCourseListHandlerTeacher extends ShowCourseListHandler {

	@Override
	protected Class<? extends CourseListView> courseListViewClass() {
		T.call(this);

		return CourseListViewTeacher.class;
	}

}
