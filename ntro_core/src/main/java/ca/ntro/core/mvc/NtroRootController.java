package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public abstract class NtroRootController extends NtroAbstractController {
	
	private NtroWindow window;
	
	protected void setWindow(NtroWindow window) {
		T.call(this);
		
		this.window = window;
	}
	
	public NtroWindow getWindow() {
		T.call(this);

		return window;
	}

	protected void addWindowViewHandler(WindowViewHandler<?> handler) {
		T.call(this);
		
		handler.setWindow(window);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewLoader.class, Constants.VIEW_LOADER_TASK_ID);
	}
}
