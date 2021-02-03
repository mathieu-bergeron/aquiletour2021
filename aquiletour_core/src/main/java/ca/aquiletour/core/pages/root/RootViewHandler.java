package ca.aquiletour.core.pages.root;

import ca.ntro.core.mvc.ViewHandler;
import ca.ntro.core.system.trace.T;

public class RootViewHandler extends ViewHandler<RootController, RootView> {
	
	@Override
	public void handle(RootController controller, RootView view) {
		T.call(this);
		
		controller.getWindow().installRootView(view);
	}
}
