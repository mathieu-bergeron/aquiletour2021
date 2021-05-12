package ca.aquiletour.core.pages.semester_list;

import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.models.SemesterList;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class SemesterListController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(viewClass(), context.lang());

		addParentViewMessageHandler(showMessageClass(), showMessageHandler());

		setModelLoader(modelClass(), 
					   context.user().getAuthToken(),
					   modelId(context));

		addSubViewLoader(subViewClass(), context().lang());
		
		addModelViewSubViewHandler(subViewClass(), viewModel());
	}

	protected abstract Class<? extends SemesterListView> viewClass();
	protected abstract Class<? extends ShowSemesterListMessage> showMessageClass();
	protected abstract ShowSemesterListHandler showMessageHandler();
	protected abstract Class<? extends SemesterView> subViewClass();
	protected abstract Class<? extends SemesterList> modelClass();
	protected abstract String modelId(NtroContext<?,?> context);
	protected abstract ModelViewSubViewHandler<?,?> viewModel();
	
	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}
}
