package ca.aquiletour.core.backend;

import ca.ntro.core.mvc.BackendRootController;

public class RootBackendController extends BackendRootController  {

	@Override
	protected void onCreate() {

		addSubController(DashboardBackendController.class, "mescours");

	}

	@Override
	protected void onFailure(Exception e) {
		
	}
}
