package ca.aquiletour.core.pages.root;

import ca.ntro.core.mvc.ViewReceptor;
import ca.ntro.core.system.trace.T;

public class RootViewReceptor extends ViewReceptor<RootController, RootView> {
	
	@Override
	public void onViewLoaded(RootController controller, RootView view) {
		T.call(this);
		
		controller.installRootView(view);
	}
}
