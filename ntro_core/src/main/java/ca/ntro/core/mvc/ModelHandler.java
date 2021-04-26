package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class      ModelHandler<M extends NtroModel>
                extends    Handler 
                implements TaskWrapper {
	
	private ModelHandlerTask<M> task = new ModelHandlerTask<M>(this);

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return task;
	}

	@Override
	public GraphTraceConnector execute() {
		T.call(this);
		
		return task.execute();
	}

	public void handleImpl(M model) {
		T.call(this);
		
		handle(model);
	}
	
	protected abstract void handle(M model);

}
