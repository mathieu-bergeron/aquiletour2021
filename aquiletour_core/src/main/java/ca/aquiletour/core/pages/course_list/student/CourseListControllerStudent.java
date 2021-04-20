package ca.aquiletour.core.pages.course_list.student;

import ca.aquiletour.core.pages.course_list.CourseListController;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.student.handlers.CourseListViewModelStudent;
import ca.aquiletour.core.pages.course_list.student.views.CourseItemViewStudent;
import ca.aquiletour.core.pages.course_list.student.views.CourseListViewStudent;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;

public class CourseListControllerStudent extends CourseListController {

	@Override
	protected Class<? extends NtroView> viewClass() {
		T.call(this);
		
		return CourseListViewStudent.class;
	}

	@Override
	protected Class<? extends NtroView> subViewClass() {
		T.call(this);
		
		return CourseItemViewStudent.class;
	}

	@Override
	protected CourseListViewModel<?> viewModel() {
		T.call(this);
		
		return new CourseListViewModelStudent();
	}
}
