package ca.aquiletour.core.pages.git;

import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowGitHandler extends ParentViewMessageHandler<RootView,
                                                                  GitView,
                                                                  ShowGitMessage> {

	@Override
	protected void handle(RootView parentView, 
			              GitView currentView, 
			              ShowGitMessage message) {
		T.call(this);
		
		// FIXME: parentView needs to be the one installed in
		//        the parent controller. We cannot do viewLoader.createView()

		parentView.showGit(currentView);
	}
}
