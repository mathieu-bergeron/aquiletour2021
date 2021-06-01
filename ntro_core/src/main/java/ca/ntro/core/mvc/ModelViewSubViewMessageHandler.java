package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public abstract class      ModelViewSubViewMessageHandler<M extends NtroModel, V extends NtroView, MSG extends NtroMessage>
                extends    Handler<NtroAbstractController>
                implements TaskWrapper {
	
	private ViewLoader subViewLoader;
	private ModelViewSubViewMessageHandlerTask<M,V,MSG> task;
	
	void setSubViewLoader(ViewLoader subViewLoader){
		T.call(this);
		
		this.subViewLoader = subViewLoader;
	}

	void setMessageId(String messageId) {
		T.call(this);

		task =  new ModelViewSubViewMessageHandlerTask<M,V,MSG>(this, messageId);
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

	public void handleImpl(V view, M model, MSG message) {
		T.call(this);
		
		handle(model, view, subViewLoader, message);
	}
	
	protected abstract void handle(M model, V view, ViewLoader subViewLoader, MSG message);

}
