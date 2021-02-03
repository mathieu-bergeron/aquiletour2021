package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public abstract class NtroRootController extends ControllerBase {
	
	private NtroWindow window;
	
	protected void setWindow(NtroWindow window) {
		T.call(this);
		
		this.window = window;
	}
	
	public NtroWindow getWindow() {
		T.call(this);

		return window;
	}
}
