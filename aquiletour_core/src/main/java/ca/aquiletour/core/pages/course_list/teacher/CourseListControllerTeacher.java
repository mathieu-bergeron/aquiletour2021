package ca.aquiletour.core.pages.course_list.teacher;

import ca.aquiletour.core.pages.course_list.CourseListController;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.teacher.handlers.CourseListViewModelTeacher;
import ca.aquiletour.core.pages.course_list.teacher.handlers.ShowCourseListHandlerTeacher;
import ca.aquiletour.core.pages.course_list.teacher.messages.SelectCourseListSubsetTeacher;
import ca.aquiletour.core.pages.course_list.teacher.messages.ShowCourseListMessageTeacher;
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
	protected CourseListViewModel<?,?> viewModel() {
		T.call(this);
		
		return new CourseListViewModelTeacher();
	}

	@Override
	protected ShowCourseListHandler showHandler() {
		T.call(this);
		
		return new ShowCourseListHandlerTeacher();
	}

	@Override
	protected Class<? extends CourseListModel> modelClass() {
		T.call(this);
		
		return CourseListModelTeacher.class;
	}

	@Override
	protected Class<? extends ShowCourseListMessage> showMessageClass() {
		T.call(this);

		return ShowCourseListMessageTeacher.class;
	}

	@Override
	protected Class<? extends SelectCourseListSubset> selectMessageClass() {
		T.call(this);

		return SelectCourseListSubsetTeacher.class;
	}
}
