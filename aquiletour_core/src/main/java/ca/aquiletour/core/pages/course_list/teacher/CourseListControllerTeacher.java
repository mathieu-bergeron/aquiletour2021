package ca.aquiletour.core.pages.course_list.teacher;

import ca.aquiletour.core.pages.course_list.CourseListController;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.teacher.handlers.CourseListViewModelTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseItemViewTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;

public class CourseListControllerTeacher extends CourseListController {

	@Override
	protected Class<? extends NtroView> viewClass() {
		T.call(this);
		
		return CourseListViewTeacher.class;
	}

	@Override
	protected Class<? extends NtroView> subViewClass() {
		T.call(this);

		return CourseItemViewTeacher.class;
	}

	@Override
	protected CourseListViewModel<?> viewModel() {
		T.call(this);
		
		return new CourseListViewModelTeacher();
	}
}
