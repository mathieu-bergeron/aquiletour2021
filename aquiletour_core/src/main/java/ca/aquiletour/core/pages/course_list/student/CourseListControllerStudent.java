package ca.aquiletour.core.pages.course_list.student;

import ca.aquiletour.core.pages.course_list.CourseListController;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.student.handlers.CourseListViewModelStudent;
import ca.aquiletour.core.pages.course_list.student.handlers.ShowCourseListHandlerStudent;
import ca.aquiletour.core.pages.course_list.student.messages.SelectCourseListSubsetStudent;
import ca.aquiletour.core.pages.course_list.student.messages.ShowCourseListMessageStudent;
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
	protected CourseListViewModel<?,?> viewModel() {
		T.call(this);
		
		return new CourseListViewModelStudent();
	}

	@Override
	protected ShowCourseListHandler showHandler() {
		T.call(this);
		
		return new ShowCourseListHandlerStudent();
	}

	@Override
	protected Class<? extends CourseListModel> modelClass() {
		T.call(this);
		
		return CourseListModelStudent.class;
	}

	@Override
	protected Class<? extends ShowCourseListMessage> showMessageClass() {
		T.call(this);

		return ShowCourseListMessageStudent.class;
	}

	@Override
	protected Class<? extends SelectCourseListSubset> selectMessageClass() {
		T.call(this);

		return SelectCourseListSubsetStudent.class;
	}
}
