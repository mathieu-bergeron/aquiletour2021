package ca.aquiletour.core.pages.home;

import ca.aquiletour.core.pages.root.AiguilleurRootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowHomeHandler extends ParentViewMessageHandler<AiguilleurRootView,
                                                                  HomeView,
                                                                  ShowHomeMessage> {

	@Override
	protected void handle(AiguilleurRootView parentView, 
			              HomeView currentView, 
			              ShowHomeMessage message) {
		T.call(this);
		
		// FIXME: parentView needs to be the one installed in
		//        the parent controller. We cannot do viewLoader.createView()

		parentView.showHome(currentView);
	}
}
