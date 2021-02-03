package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class Handler {

	private ControllerBase controller;

	public void setController(ControllerBase controller) {
		T.call(this);
		
		this.controller = controller;
	}
	
	protected ControllerBase getController() {
		T.call(this);

		return controller;
	}

}
