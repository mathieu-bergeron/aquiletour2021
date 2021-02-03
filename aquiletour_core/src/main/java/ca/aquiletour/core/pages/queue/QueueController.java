package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class QueueController extends NtroController<RootController> {

	@Override
	protected void initialize() {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}
}
