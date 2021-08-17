package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;


class   ModelMessageHandlerTask<M   extends NtroModel, 
                                MSG extends NtroMessage>
extends NtroTaskAsync {
	
	private ModelMessageHandler<M,MSG> handler;
	private String messageId;
	
	public ModelMessageHandlerTask(ModelMessageHandler<M, MSG> handler, String messageId) {
		T.call(this);

		this.handler = handler;
		this.messageId = messageId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTaskAsync() {
		T.call(this);

		M model = (M) ((ModelLoader) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID)).getModel();

		MustNot.beNull(model);
		
		MessageHandlerTask<NtroMessage> messageHandler = (MessageHandlerTask<NtroMessage>) getPreviousTask(MessageHandlerTask.class, messageId);

		MustNot.beNull(messageHandler);

		handler.handleImpl(model, (MSG) messageHandler.getMessage());
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
