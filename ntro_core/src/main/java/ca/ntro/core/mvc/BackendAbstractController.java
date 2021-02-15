package ca.ntro.core.mvc;

import ca.ntro.core.Ntro;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.NtroMessage;

public abstract class BackendAbstractController extends AnyController implements TaskWrapper {

	private NtroTask mainTask = new ContainerTask();

	private ModelStore modelStore;

	public void setModelStore(ModelStore modelStore) {
		T.call(this);
		
		this.modelStore = modelStore;
	}
	
	ModelStore getModelStore() {
		T.call(this);
		
		return modelStore;
	}

	protected abstract void onCreate();
	protected abstract void onFailure(Exception e);

	protected void addMessageHandler(Class<? extends NtroMessage> messageClass, MessageHandler<?,?> handler) {
		T.call(this);

		NtroTask message = MessageFactory.getIncomingMessage(messageClass);
		
		String messageId = Ntro.introspector().getSimpleNameForClass(messageClass);
		message.setTaskId(messageId);
		handler.setMessageId(messageId);
		
		handler.setController(this);

		handler.getTask().addPreviousTask(message);
	}

	protected <C extends BackendController<?>> void addSubController(Class<C> controllerClass, String controllerId) {
		T.call(this);

		BackendControllerFactory.createBackendController(controllerClass, this);

	}

	@Override
	public NtroTask getTask() {
		T.call(this);

		return mainTask;
	}

	@Override
	public void execute() {
		T.call(this);
		
		mainTask.execute();
	}


	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder) {

		ModelLoader loader = modelStore.getLoaderImpl(modelClass, authToken, firstPathName, pathRemainder);
		
		loader.execute();
		
		return (M) loader.getModel();
	}
}
