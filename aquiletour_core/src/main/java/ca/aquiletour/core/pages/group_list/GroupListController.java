package ca.aquiletour.core.pages.group_list;

import ca.aquiletour.core.pages.group_list.handlers.GroupListViewModel;
import ca.aquiletour.core.pages.group_list.handlers.ShowGroupListHandler;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.messages.ShowGroupListMessage;
import ca.aquiletour.core.pages.group_list.models.GroupListModel;
import ca.aquiletour.core.pages.group_list.views.GroupDescriptionView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class GroupListController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(GroupListView.class, context.lang());

		addParentViewMessageHandler(ShowGroupListMessage.class, new ShowGroupListHandler());

		setModelLoader(GroupListModel.class, 
					   context.user().getAuthToken(),
					   context.user().getId());

		addSubViewLoader(GroupDescriptionView.class, context().lang());
		
		addModelViewSubViewMessageHandler(GroupDescriptionView.class, SelectGroupListSubset.class, new GroupListViewModel());
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
