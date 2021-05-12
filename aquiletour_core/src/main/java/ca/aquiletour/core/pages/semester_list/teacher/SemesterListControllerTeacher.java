package ca.aquiletour.core.pages.semester_list.teacher;

import ca.aquiletour.core.pages.semester_list.SemesterListController;
import ca.aquiletour.core.pages.semester_list.admin.messages.ShowSemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.models.SemesterList;
import ca.aquiletour.core.pages.semester_list.teacher.handlers.SemesterListViewModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.handlers.ShowSemesterListHandlerTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.messages.ShowSemesterListTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterListViewTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterViewTeacher;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public class SemesterListControllerTeacher extends SemesterListController {

	@Override
	protected Class<? extends SemesterListView> viewClass() {
		T.call(this);
		
		return SemesterListViewTeacher.class;
	}

	@Override
	protected Class<? extends SemesterView> subViewClass() {
		T.call(this);
		
		return SemesterViewTeacher.class;
	}

	@Override
	protected Class<? extends SemesterList> modelClass() {
		T.call(this);
		
		return SemesterListTeacher.class;
	}

	@Override
	protected String modelId(NtroContext<?, ?> context) {
		T.call(this);
		
		return context.user().getId();
	}

	@Override
	protected ModelViewSubViewHandler<?, ?> viewModel() {
		T.call(this);
		
		return new SemesterListViewModelTeacher();
	}

	@Override
	protected Class<? extends ShowSemesterListMessage> showMessageClass() {
		T.call(this);
		
		return ShowSemesterListTeacher.class;
	}

	@Override
	protected ShowSemesterListHandler showMessageHandler() {
		T.call(this);
		
		return new ShowSemesterListHandlerTeacher();
	}
}
