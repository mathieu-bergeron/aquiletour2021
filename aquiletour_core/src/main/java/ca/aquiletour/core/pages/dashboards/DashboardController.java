package ca.aquiletour.core.pages.dashboards;

import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class DashboardController extends NtroController<AiguilleurRootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(viewClass(), currentContext().lang());
		
		setModelLoader(DashboardModel.class, 
				       currentContext().user().getAuthToken(),
				       currentContext().user().getId());

		installParentViewMessageHandler();
		
		// TODO: add model handler to pre-load models of each courses
		//       on the server, model pre-loading does nothing (or is restricted by path)
	}
	
	protected abstract Class<? extends DashboardView> viewClass();
	protected abstract void installParentViewMessageHandler();
	

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}


}
