package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class Handler<C extends NtroAbstractController> {

	private C controller;

	void setController(C controller) {
		T.call(this);
		
		this.controller = (C) controller;
	}
	
	protected C getController() {
		T.call(this);

		return controller;
	}

}
