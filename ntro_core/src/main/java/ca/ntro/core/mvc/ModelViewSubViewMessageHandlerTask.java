package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;

public class ModelViewSubViewMessageHandlerTask<M extends NtroModel, V extends NtroView, MSG extends NtroMessage> extends NtroTaskAsync {
	
	private ModelViewSubViewMessageHandler<M,V,MSG> handler;
	private String messageId;
	
	public ModelViewSubViewMessageHandlerTask(ModelViewSubViewMessageHandler<M,V,MSG> handler, String messageId) {
		T.call(this);

		this.handler = handler;
		this.messageId = messageId;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		ViewCreatorTask viewCreator = (ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);

		ModelLoader modelLoader = (ModelLoader) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID);
		
		MustNot.beNull(modelLoader);

		@SuppressWarnings("unchecked")
		M model = (M) modelLoader.getModel();

		MustNot.beNull(viewCreator);
		if(model == null) {
			System.out.println("modelLoader " + modelLoader.getClass().getSimpleName() );
		}
		MustNot.beNull(model);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		MessageHandlerTask messageHandlerTask = (MessageHandlerTask) getPreviousTask(MessageHandlerTask.class, messageId);
		MustNot.beNull(messageHandlerTask);

		@SuppressWarnings("unchecked")
		V view = (V) viewCreator.getView();
		
		MustNot.beNull(view);

		handler.handleImpl(view, model, (MSG) messageHandlerTask.getMessage());
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
