package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.messages.NtroMessage;
import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;



class   ModelMessageHandlerTask<M   extends NtroModel, 
                                MSG extends NtroMessage>
extends NtroTaskImpl {
	
	private ModelMessageHandler<M,MSG> handler;
	
	public ModelMessageHandlerTask(ModelMessageHandler<M, MSG> handler) {
		T.call(this);

		this.handler = handler;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		@SuppressWarnings("unchecked")
		M model = (M) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID).getModel();

		MustNot.beNull(model);
		
		MSG message = (MSG) getPreviousTask(NtroMessage.class, Constants.MESSAGE_TASK_ID);

		MustNot.beNull(message);

		handler.handleImpl(model, message);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
