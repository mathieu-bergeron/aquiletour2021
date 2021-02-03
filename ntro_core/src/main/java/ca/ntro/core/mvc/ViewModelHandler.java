package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class      ViewModelHandler<V extends NtroView, M extends NtroModel>
                extends    Handler 
                implements TaskWrapper {
	
	private ViewModelHandlerTask<V,M> task = new ViewModelHandlerTask<V,M>(this);

	@Override
	public NtroTask getTask() {
		T.call(this);
		
		return task;
	}

	@Override
	public void execute() {
		T.call(this);
		
		task.execute();
	}

	public void handleImpl(V view, M model) {
		T.call(this);
		
		handle(view, model);
	}
	
	protected abstract void handle(V view, M model);

}
