package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class DashboardController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(viewClass(), context.lang());
		
		requestModel(context);

		installParentViewMessageHandler();
		
		// TODO: add model handler to pre-load models of each courses
		//       on the server, model pre-loading does nothing (or is restricted by path)
	}
	
	protected abstract Class<? extends DashboardView> viewClass();
	protected abstract void installParentViewMessageHandler();
	

	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
		
		System.out.println("onContextChange");
		
		//requestModel(context);
	}

	private void requestModel(NtroContext<?,?> context) {
		T.call(this);

		System.out.println("requestModel");

		setModelLoader(DashboardModel.class, 
					   context.user().getAuthToken(),
					   context.user().getId());
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}
}
