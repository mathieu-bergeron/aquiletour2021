package ca.aquiletour.core.pages.group_list.handlers;


import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.models.GroupListModel;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class GroupListViewModel extends ModelViewSubViewMessageHandler<GroupListModel, GroupListView, SelectGroupListSubset> {
	
	@Override
	protected void handle(GroupListModel model, GroupListView view, ViewLoader subViewLoader, SelectGroupListSubset message) {
		T.call(this);
		
		T.here();
	}
}
