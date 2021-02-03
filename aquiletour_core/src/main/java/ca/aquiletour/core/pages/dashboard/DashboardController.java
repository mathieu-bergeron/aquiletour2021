package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class DashboardController extends NtroController<RootController> {

	@Override
	protected void initialize() {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}

}
