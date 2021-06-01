package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
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
	}
	
	protected abstract Class<? extends DashboardView> viewClass();
	protected abstract Class<? extends DashboardModel<?>> modelClass();
	protected abstract void installParentViewMessageHandler();
	
	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);
		
		System.out.println("onContextChange");
		
		requestModel(context);
	}

	private void requestModel(NtroContext<?,?> context) {
		T.call(this);

		setModelLoader(modelClass(), 
					   context.user().getAuthToken(),
					   context.user().getId());
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}
}
