package ca.aquiletour.core.pages.course_list.teacher.handlers;

import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseListViewModelTeacher extends CourseListViewModel<CourseListModelTeacher, CourseListViewTeacher> {

	@Override
	protected void handle(CourseListModelTeacher model, CourseListViewTeacher view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
	}

}
