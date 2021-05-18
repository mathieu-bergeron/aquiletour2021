package ca.aquiletour.core.pages.course_list;

import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.handlers.ShowCourseListHandler;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;

public abstract class CourseListController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(viewClass(), context.lang());

		addParentViewMessageHandler(showMessageClass(), showHandler());

		setModelLoader(modelClass(), 
					   context.user().getAuthToken(),
					   context.user().getId());

		addSubViewLoader(subViewClass(), context().lang());
		
		addModelViewSubViewMessageHandler(subViewClass(), selectMessageClass(), viewModel());
	}

	protected abstract Class<? extends ShowCourseListMessage> showMessageClass();
	protected abstract Class<? extends SelectCourseListSubset> selectMessageClass();
	protected abstract ShowCourseListHandler showHandler();
	protected abstract Class<? extends CourseListModel> modelClass();
	protected abstract Class<? extends NtroView> viewClass();
	protected abstract Class<? extends NtroView> subViewClass();
	protected abstract CourseListViewModel<?,?> viewModel();
	
	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}
}
