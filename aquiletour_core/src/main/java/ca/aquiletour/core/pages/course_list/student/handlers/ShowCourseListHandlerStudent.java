package ca.aquiletour.core.pages.course_list.student.handlers;

import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.student.views.CourseListViewStudent;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.system.trace.T;

public class ShowCourseListHandlerStudent extends ShowCourseListHandler {

	@Override
	protected Class<? extends CourseListView> courseListViewClass() {
		T.call(this);
		
		return CourseListViewStudent.class;
	}

}
