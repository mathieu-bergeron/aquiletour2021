package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class Handler<C extends AnyController> {

	private C controller;

	void setController(AnyController controller) {
		T.call(this);
		
		this.controller = (C) controller;
	}
	
	protected C getController() {
		T.call(this);

		return controller;
	}

}
