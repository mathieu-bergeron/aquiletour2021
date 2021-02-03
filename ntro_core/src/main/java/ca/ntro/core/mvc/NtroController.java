package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public abstract class NtroController<CB extends ControllerBase> extends ControllerBase {
	
	private CB parentController;
	
	void setParentController(CB parentController) {
		T.call(this);
		
		this.parentController = parentController;
	}

	public ParentController getParentController() {
		T.call(this);
		
		return parentController.asParentController();
	}
}
