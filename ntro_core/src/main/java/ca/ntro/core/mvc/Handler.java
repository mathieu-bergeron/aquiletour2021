package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class Handler<C extends AnyController> {

	private C controller;

	void setController(C controller) {
		T.call(this);
		
		this.controller = controller;
	}
	
	protected C getController() {
		T.call(this);

		return controller;
	}

}
