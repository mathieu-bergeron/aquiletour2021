package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;

public abstract class      ModelViewSubViewHandler<M extends NtroModel, V extends NtroView>
                extends    Handler<NtroAbstractController>
                implements TaskWrapper {
	
	private ViewLoader subViewLoader;
	private ModelViewSubViewHandlerTask<M,V> task = new ModelViewSubViewHandlerTask<M,V>(this);
	
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

	public void handleImpl(V view, M model) {
		T.call(this);
		
		handle(model, view, subViewLoader);
	}
	
	protected abstract void handle(M model, V view, ViewLoader subViewLoader);

}
