package ca.aquiletour.core.backend;

import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroRootController;

                                           // TODO: create NtroBackendController
public class RootBackendController extends NtroRootController  {

	@Override
	protected void onCreate() {

		addSubController(DashboardBackendController.class, "mescours");

	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

}
