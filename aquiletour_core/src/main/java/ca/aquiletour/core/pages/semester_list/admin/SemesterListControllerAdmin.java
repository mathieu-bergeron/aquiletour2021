package ca.aquiletour.core.pages.semester_list.admin;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.SemesterListController;
import ca.aquiletour.core.pages.semester_list.admin.handlers.SemesterListViewModelAdmin;
import ca.aquiletour.core.pages.semester_list.admin.handlers.ShowSemesterListHandlerAdmin;
import ca.aquiletour.core.pages.semester_list.admin.messages.ShowSemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.admin.views.SemesterListViewAdmin;
import ca.aquiletour.core.pages.semester_list.admin.views.SemesterViewAdmin;
import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public class SemesterListControllerAdmin extends SemesterListController {

	@Override
	protected Class<? extends SemesterListView> viewClass() {
		T.call(this);
		
		return SemesterListViewAdmin.class;
	}

	@Override
	protected Class<? extends SemesterView> subViewClass() {
		T.call(this);
		
		return SemesterViewAdmin.class;
	}

	@Override
	protected Class<? extends SemesterListModel<?>> modelClass() {
		T.call(this);
		
		return SemesterListModelAdmin.class;
	}

	@Override
	protected String modelId(NtroContext<?, ?> context) {
		T.call(this);
		
		return Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID;
	}

	@Override
	protected ModelViewSubViewHandler<?, ?> viewModel() {
		T.call(this);
		
		return new SemesterListViewModelAdmin();
	}

	@Override
	protected Class<? extends ShowSemesterListMessage> showMessageClass() {
		T.call(this);
		
		return ShowSemesterListAdmin.class;
	}

	@Override
	protected ShowSemesterListHandler showMessageHandler() {
		T.call(this);
		
		return new ShowSemesterListHandlerAdmin();
	}
}
