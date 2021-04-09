package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class      ModelSubModelViewSubViewHandler<M extends NtroModel, SM extends NtroModel, V extends NtroView>
                extends    Handler 
                implements TaskWrapper {
	
	private ViewLoader subViewLoader;
	private ModelSubModelViewSubViewHandlerTask<M,SM,V> task = new ModelSubModelViewSubViewHandlerTask<M,SM,V>(this);
	
	void setSubViewLoader(ViewLoader subViewLoader){
		T.call(this);
		
		this.subViewLoader = subViewLoader;
	}

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

	public void handleImpl(V view, M model, SM subModel) {
		T.call(this);
		
		handle(model, subModel, view, subViewLoader);
	}
	
	protected abstract void handle(M model, SM subModel, V view, ViewLoader subViewLoader);

}
