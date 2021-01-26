package ca.aquiletour.core.pages.rootpage;

import ca.aquiletour.core.messages.OpenDashboardMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageReceptionTask;

public class OpenDashboardReceptor extends MessageReceptionTask {
	
	RootpageController rootpageController;

	public OpenDashboardReceptor(RootpageController rootpageController) {
		T.call(this);

		this.rootpageController = rootpageController;
	}

	@Override
	protected void runTask() {
		T.call(this);

		// XXX: just to show the idea (not useful here)
		OpenDashboardMessage message = (OpenDashboardMessage) getMessage();
		
		rootpageController.openDashboard();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
