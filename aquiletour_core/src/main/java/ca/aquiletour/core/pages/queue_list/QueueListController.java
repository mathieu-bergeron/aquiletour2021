package ca.aquiletour.core.pages.queue_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.queue_list.handlers.QueueListViewModel;
import ca.aquiletour.core.pages.queue_list.handlers.ShowQueueListHandler;
import ca.aquiletour.core.pages.queue_list.messages.ShowQueueListMessage;
import ca.aquiletour.core.pages.queue_list.models.QueueListModel;
import ca.aquiletour.core.pages.queue_list.views.QueueListItemView;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class QueueListController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(QueueListView.class, "fr");
		
		setModelLoader(QueueListModel.class, "admin", Constants.QUEUE_LIST_ID);
		
		addParentViewMessageHandler(ShowQueueListMessage.class, new ShowQueueListHandler());
		addSubViewLoader(QueueListItemView.class, context().lang());
		addModelViewSubViewHandler(QueueListItemView.class, new QueueListViewModel());
		
		// TODO
	}

	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
