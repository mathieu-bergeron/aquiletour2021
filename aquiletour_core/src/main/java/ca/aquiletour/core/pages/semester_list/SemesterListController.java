package ca.aquiletour.core.pages.semester_list;

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

public class SemesterListController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(SemesterListView.class, context.lang());

		addParentViewMessageHandler(ShowSemesterListMessage.class, new ShowSemesterListHandler());

		setModelLoader(SemesterListModel.class, 
					   context.user().getAuthToken(),
					   context.user().getId());

		addSubViewLoader(SemesterView.class, context().lang());
		
		addModelViewSubViewHandler(SemesterView.class, new SemesterListViewModel());
	}
	
	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}
}
