package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class Handler {

	private NtroAbstractController controller;

	public void setController(NtroAbstractController controller) {
		T.call(this);
		
		this.controller = controller;
	}
	
	protected NtroAbstractController getController() {
		T.call(this);

		return controller;
	}

}
