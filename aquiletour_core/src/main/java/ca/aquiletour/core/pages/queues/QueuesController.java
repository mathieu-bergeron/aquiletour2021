package ca.aquiletour.core.pages.queues;

import ca.aquiletour.core.pages.queues.messages.ShowQueuesHandler;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class QueuesController extends NtroController<AiguilleurRootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(QueuesView.class, "fr");
		
		setModelLoader(QueuesModel.class, "admin", "openQueues");
		
		addParentViewMessageHandler(ShowQueuesMessage.class, new ShowQueuesHandler());
		addSubViewLoader(QueueSummaryView.class, currentContext().lang());
		addModelViewSubViewHandler(QueueSummaryView.class, new QueuesViewModel());
		
		// TODO
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
