package ca.aquiletour.core.pages.course_list;

import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.course_list.views.CourseSummaryView;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.semester_list.handlers.SemesterListViewModel;
import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CourseListController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(CourseListView.class, context.lang());

		addParentViewMessageHandler(ShowCourseListMessage.class, new ShowCourseListHandler());

		setModelLoader(CourseListModel.class, 
					   context.user().getAuthToken(),
					   context.user().getId());

		addSubViewLoader(CourseSummaryView.class, context().lang());
		
		addModelViewSubViewHandler(CourseSummaryView.class, new CourseListViewModel());
	}
	
	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}
}
