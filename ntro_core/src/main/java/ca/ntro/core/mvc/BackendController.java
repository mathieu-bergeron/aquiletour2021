package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public abstract class BackendController <PC extends BackendAbstractController> extends BackendAbstractController{
	
	private PC parentController;
	
	public void setParentController(BackendAbstractController parentController) {
		T.call(this);

		this.parentController = (PC) parentController;
	}
	

}
