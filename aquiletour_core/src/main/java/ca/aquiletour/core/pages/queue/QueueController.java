package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.DashboardViewModel;
import ca.aquiletour.core.pages.queue.handlers.AddAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.DeleteAppointmentHandler;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.services.stores.NetworkStore;
import ca.ntro.core.system.trace.T;

public abstract class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);
		
		// XXX: is replaced by actual loader in ShowQueueHandler
		setModelLoader(new EmptyModelLoader());

		setViewLoader(QueueView.class, currentContext().getLang());

		addSubViewLoader(AppointmentView.class, currentContext().getLang());

		// (1) installing a ModelMessageHandler even if there is no modelLoader
		//      this means it will block until the model is loaded
		addModelMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());

		addModelMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());

		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());

		// (2) the modelLoader is installed after a ShowQueueMessage!
		addControllerMessageHandler(ShowQueueMessage.class, new ShowQueueHandler());
//		setViewLoader(viewClass(), currentContext().getLang());
//		
//		setModelLoader(NetworkStore.getLoader(DashboardModel.class, 
//				                              currentContext().getUser().getAuthToken(),
//				                              currentContext().getUser().getId()));
//
//		installParentViewMessageHandler();
//
//		addSubViewLoader(AppointmentView.class, currentContext().getLang());
//		
//		addModelViewSubViewHandler(AppointmentView.class, new QueueViewModel());
//		
//		// TODO: add model handler to pre-load models of each courses
//		//       on the server, model pre-loading does nothing (or is restricted by path)

	}
	
	protected abstract Class<? extends QueueView> viewClass();
	protected abstract void installParentViewMessageHandler();
	
	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
