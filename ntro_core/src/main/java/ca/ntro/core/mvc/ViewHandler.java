package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class ViewHandler<CB extends NtroAbstractController, V extends NtroView> 
                extends Handler<NtroAbstractController> implements TaskWrapper {
	
	private NtroTask mainTask = new ViewHandlerTask<CB, V>(this);

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return mainTask;
	}

	@Override
	public GraphTraceConnector execute() {
		T.call(this);
		
		return mainTask.execute();
	}
	
	@SuppressWarnings("unchecked")
	void handle(V view) {
		T.call(this);
		
		handle((CB) getController(), view);
	}

	public abstract void handle(CB controller, V view);
}
