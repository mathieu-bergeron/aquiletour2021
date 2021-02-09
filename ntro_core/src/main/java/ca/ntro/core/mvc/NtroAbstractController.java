package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_HANDLER_TASK_ID;


abstract class NtroAbstractController implements TaskWrapper {
	
	private NtroTask mainTask = new ContainerTask();
	private NtroContext context;
	private Path path;

	protected abstract void onCreate();
	protected abstract void onChangeContext(NtroContext previousContext);
	protected abstract void onFailure(Exception e);

	public NtroAbstractController() {
		T.call(this);
		
		mainTask.setTaskId(this.getClass().getSimpleName());
		
		addDefaultTasks();
	}

	private void addDefaultTasks() {
		T.call(this);
		
		setModelLoader(new EmptyModelLoader());
	}
	@Override
	public NtroTask getTask() {
		T.call(this);

		return mainTask;
	}
	
	@Override
	public void execute() {
		T.call(this);
		
		getTask().execute();
	}
	
	void setContext(NtroContext context) {
		this.context = context;
	}

	void setPath(Path path) {
		T.call(this);

		this.path = path;
	}
	
	public NtroContext currentContext() {
		T.call(this);
		
		return context;
	}

	
	protected <C extends NtroController<?>> void addSubController(Class<C> controllerClass, String controllerId) {
		T.call(this);
		
		if(path.startsWith(controllerId) || path.startsWith("*")) {
			Path pathRemainder = path.removePrefix(controllerId);
			
			C subController = ControllerFactory.createController(controllerClass, pathRemainder, this, context);
			
			getTask().addNextTask(subController.getTask());

		}else {
			
			System.err.println("[WARNING]: subController not added: " + controllerId);

		}

	}
	
	protected void addSubViewLoader(Class<? extends NtroView> subViewClass, String lang) {
		T.call(this);

		ViewLoader viewLoader = ViewLoaders.getViewLoader(subViewClass, lang);
		
		MustNot.beNull(viewLoader);
		
		// FIXME: it should be ok to reuse a viewLoader at DONE
		//        it simply means the files are already loaded
		viewLoader.reset();
		
		viewLoader.setTaskId(subViewClass.getSimpleName());
		
		getTask().addSubTask(viewLoader);
	}

	protected void addMessageHandler(Class<? extends NtroMessage> messageClass, MessageHandler handler) {
		T.call(this);
		
		NtroTask message = MessageFactory.getIncomingMessage(messageClass);
		message.setTaskId(messageClass.getSimpleName());
		
		message.addNextTask(handler);
	}

	protected void addModelMessageHandler(Class<? extends NtroMessage> messageClass, ModelMessageHandler<?,?> handler) {
		T.call(this);
		
		String messageId = messageClass.getSimpleName();

		NtroMessage message = MessageFactory.getIncomingMessage(messageClass);
		message.setTaskId(messageId);
		
		handler.setMessageId(messageId);

		handler.getTask().addPreviousTask(message);
		
		addPreviousTaskTo(handler.getTask(), ModelLoader.class, MODEL_LOADER_TASK_ID);
	}
	
	protected void setViewLoader(Class<? extends NtroView> viewClass, String lang) {
		T.call(this);
		
		ViewLoader viewLoader = ViewLoaders.getViewLoader(viewClass, lang);
		
		MustNot.beNull(viewLoader);
		
		// FIXME: it should be ok to reuse a viewLoader at DONE
		//        it simply means the files are already loaded
		viewLoader.reset();

		viewLoader.setTaskId(VIEW_LOADER_TASK_ID);
		getTask().addSubTask(viewLoader);
		
		ViewCreatorTask viewCreator = new ViewCreatorTask();
		viewCreator.setTaskId(VIEW_CREATOR_TASK_ID);
		
		getTask().addSubTask(viewCreator);
		
		viewCreator.addPreviousTask(viewLoader);
		
		addPreviousTaskTo(ModelViewHandlerTask.class, VIEW_MODEL_TASK_ID, viewCreator);
		addPreviousTaskTo(ViewHandlerTask.class, VIEW_HANDLER_TASK_ID, viewCreator);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(Class<NT> taskClass, String taskId, NtroTask previousTask) {
		T.call(this);

		NT task = getTask().getSubTask(taskClass, taskId);
		
		if(task != null) {
			task.addPreviousTask(previousTask);
		}
	}
	
	public void setModelLoader(ModelLoader modelLoader) {
		T.call(this);
		
		modelLoader.setTaskId(MODEL_LOADER_TASK_ID);
		getTask().addSubTask(modelLoader);
		
		addPreviousTaskTo(ModelViewHandlerTask.class, VIEW_MODEL_TASK_ID, modelLoader);
	}

	protected void setModelViewHandler(ModelViewHandler<?,?> handler) {
		T.call(this);
		
		NtroTask task = handler.getTask();
		
		getTask().addSubTask(task);
		
		addPreviousTaskTo(task, ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
		addPreviousTaskTo(task, ModelLoader.class, MODEL_LOADER_TASK_ID);
	}

	protected void addModelViewSubViewHandler(Class<? extends NtroView> subViewClass, ModelViewSubViewHandler<?,?> handler) {
		T.call(this);

		NtroTask task = handler.getTask();
		
		getTask().addSubTask(task);
		
		String subViewLoaderTaskId = subViewClass.getSimpleName();
		
		ViewLoader subViewLoader = (ViewLoader) getTask().getSubTask(ViewLoader.class, subViewLoaderTaskId);
		handler.setSubViewLoader(subViewLoader);
		
		addPreviousTaskTo(task, ViewLoader.class, subViewLoaderTaskId);
		addPreviousTaskTo(task, ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
		addPreviousTaskTo(task, ModelLoader.class, MODEL_LOADER_TASK_ID);
	}


	protected <NT extends NtroTask> void addPreviousTaskTo(NtroTask nextTask, Class<NT> taskClass, String taskId) {
		T.call(this);
		
		NT previousTask = getTask().getSubTask(taskClass, taskId);
		
		if(previousTask != null) {
			nextTask.addPreviousTask(previousTask);
		}
	}

	protected void setViewHandler(ViewHandler<?,?> handler) {
		T.call(this);
		
		handler.setController(this);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
	}


	protected void addViewMessageHandler(Class<? extends NtroMessage> messageClass, ViewMessageHandler<?,?> handler) {
		T.call(this);

		getTask().addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
	}

	public NtroView getView() {
		T.call(this);
		
		NtroView view = getTask().getSubTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID).getView();
		
		return view;
	}

}
