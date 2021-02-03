package ca.aquiletour.server.pages.queue;


import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.web.pages.dashboard.DashboardControllerWeb;
import ca.aquiletour.web.pages.queue.QueueControllerWeb;
import ca.ntro.core.mvc.view.ViewLoader;

public class QueueControllerServer extends QueueControllerWeb {

	public QueueControllerServer(RootController parentController) {
		super(parentController);
	}

	@Override
	protected ViewLoader createAppointmentViewLoader(String lang) {
		// TODO Auto-generated method stub
		return null;
	}
}
