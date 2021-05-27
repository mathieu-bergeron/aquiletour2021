package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		// empty model loader until the ShowQueue message
		setModelLoader(new EmptyModelLoader());

		setViewLoader(viewClass(), context().lang());
		addSubViewLoader(subViewClass(), context().lang());
		
		addControllerMessageHandler(showMessageClass(), showHandler());

		addModelViewSubViewHandler(subViewClass(), viewModel()); 

	}
	
	protected abstract QueueViewModel<?> viewModel();
	protected abstract ShowQueueHandler<?, ?> showHandler();
	protected abstract Class<? extends ShowQueueMessage> showMessageClass();
	protected abstract Class<? extends AppointmentView> subViewClass();
	protected abstract Class<? extends QueueView> viewClass();
	
	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
	}


}
