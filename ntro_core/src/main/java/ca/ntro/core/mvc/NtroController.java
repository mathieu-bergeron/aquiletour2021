package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public abstract class NtroController<AC extends NtroAbstractController> extends NtroAbstractController {
	
	private AC parentController;
	
	void setParentController(AC parentController) {
		T.call(this);
		
		this.parentController = parentController;
	}

	AC getParentController() {
		T.call(this);
		
		return parentController;
	}

	protected void addParentViewMessageHandler(Class<? extends NtroMessage> messageClass, ParentViewMessageHandler<?,?,?> handler) {
		T.call(this);
		
		handler.setParentView(parentController.getView());

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewLoader.class, Constants.VIEW_LOADER_TASK_ID);
	}
}
