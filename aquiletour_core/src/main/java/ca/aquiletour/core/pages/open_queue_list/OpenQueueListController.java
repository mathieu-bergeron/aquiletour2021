package ca.aquiletour.core.pages.open_queue_list;

import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListHandler;
import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class OpenQueueListController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(OpenQueueListView.class, "fr");
		
		setModelLoader(OpenQueueListModel.class, "admin", "openQueues");
		
		addParentViewMessageHandler(ShowOpenQueueListMessage.class, new ShowOpenQueueListHandler());
		addSubViewLoader(OpenQueueView.class, context().lang());
		addModelViewSubViewHandler(OpenQueueView.class, new OpenQueueListViewModel());
		
		// TODO
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
