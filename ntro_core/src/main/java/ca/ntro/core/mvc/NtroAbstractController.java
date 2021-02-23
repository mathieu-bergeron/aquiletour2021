package ca.ntro.core.mvc;

import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.GraphTraceConnector;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageFactory;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID;

import static ca.ntro.core.mvc.Constants.VIEW_HANDLER_TASK_ID;


abstract class NtroAbstractController extends AnyController implements TaskWrapper {

	private NtroTask mainTask = new ContainerTask();
	private NtroTask initTasks = new ContainerTask();
	private NtroContext context;
	private Path path;
	
	protected abstract void onCreate();
	protected abstract void onChangeContext(NtroContext previousContext);
	protected abstract void onFailure(Exception e);

	public NtroAbstractController() {
		T.call(this);
		
		mainTask.setTaskId(Ntro.introspector().getSimpleNameForClass(this.getClass()));
		initTasks.setTaskId("InitTasks");
		
		mainTask.addSubTask(initTasks);

		//addDefaultTasks();
	}

	private void addDefaultTasks() {
		T.call(this);
		
		// XXX: should add EmptyModelLoder
		//      only when needed

		//setModelLoader(new EmptyModelLoader());
		setViewLoader(new EmptyViewLoader());
	}
	@Override
	public NtroTask getTask() {
		T.call(this);

		return mainTask;
	}

	@Override
	public GraphTraceConnector execute() {
		T.call(this);

		return getTask().execute();
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
			initTasks.addNextTask(subController.getTask());

		}
	}

	protected void addSubViewLoader(Class<? extends NtroView> subViewClass, String lang) {
		T.call(this);

		ViewLoader viewLoader = ViewLoaders.getViewLoader(subViewClass, lang);

		MustNot.beNull(viewLoader);

		// FIXME: it should be ok to reuse a viewLoader at DONE
		//        it simply means the files are already loaded
		viewLoader.resetTask();

		viewLoader.setTaskId(Ntro.introspector().getSimpleNameForClass(subViewClass));

		getTask().addSubTask(viewLoader);
	}

	protected void addMessageHandler(Class<? extends NtroMessage> messageClass, MessageHandler<?,?> handler) {
		T.call(this);

		NtroTask message = MessageFactory.getIncomingMessage(messageClass);
		
		String messageId = Ntro.introspector().getSimpleNameForClass(messageClass);
		message.setTaskId(messageId);
		handler.setMessageId(messageId);
		
		handler.setController(this);

		handler.getTask().addPreviousTask(message);
	}

	protected void addModelMessageHandler(Class<? extends NtroMessage> messageClass, ModelMessageHandler<?,?> handler) {
		T.call(this);

		String messageId = Ntro.introspector().getSimpleNameForClass(messageClass);

		NtroMessage message = MessageFactory.getIncomingMessage(messageClass);
		message.setTaskId(messageId);

		handler.setMessageId(messageId);

		handler.getTask().addPreviousTask(message);

		addPreviousTaskTo(handler.getTask(), ModelLoader.class, MODEL_LOADER_TASK_ID);
	}

	protected void setViewLoader(ViewLoader viewLoader) {
		T.call(this);

		// FIXME: it should be ok to reuse a viewLoader at DONE
		//        it simply means the files are already loaded
		viewLoader.resetTask();
		
		ViewLoader currentViewLoader = initTasks.getSubTask(ViewLoader.class, VIEW_LOADER_TASK_ID);

		if(currentViewLoader == null) {
			
			viewLoader.setTaskId(VIEW_LOADER_TASK_ID);
			initTasks.addSubTask(viewLoader);

			ViewCreatorTask viewCreator = new ViewCreatorTask();
			viewCreator.setTaskId(VIEW_CREATOR_TASK_ID);

			initTasks.addSubTask(viewCreator);

			viewCreator.addPreviousTask(viewLoader);

			addPreviousTaskTo(ModelViewHandlerTask.class, VIEW_MODEL_TASK_ID, viewCreator);
			addPreviousTaskTo(ViewHandlerTask.class, VIEW_HANDLER_TASK_ID, viewCreator);
			
		}else {
			
			viewLoader.setTaskId(VIEW_LOADER_TASK_ID);
			currentViewLoader.replaceWith(viewLoader);
			
		}
	}

	protected void setViewLoader(Class<? extends NtroView> viewClass, String lang) {
		T.call(this);

		ViewLoader viewLoader = ViewLoaders.getViewLoader(viewClass, lang);

		MustNot.beNull(viewLoader);
		
		setViewLoader(viewLoader);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(Class<NT> taskClass, String taskId, NtroTask previousTask) {
		T.call(this);

		NT task = initTasks.getSubTask(taskClass, taskId);
		if(task == null) {
			task = mainTask.getSubTask(taskClass, taskId);
		}

		if(task != null) {
			task.addPreviousTask(previousTask);
		}
	}

	public void setModelLoader(ModelLoader modelLoader) {
		T.call(this);

		ModelLoader currentModelLoader = mainTask.getSubTask(ModelLoader.class, MODEL_LOADER_TASK_ID);
		
		if(currentModelLoader == null) {

			modelLoader.setTaskId(MODEL_LOADER_TASK_ID);
			mainTask.addSubTask(modelLoader);

			addPreviousTaskTo(ModelViewHandlerTask.class, VIEW_MODEL_TASK_ID, modelLoader);
			
		}else {
			
			modelLoader.setTaskId(MODEL_LOADER_TASK_ID);
			currentModelLoader.asTask().replaceWith(modelLoader);
		}
	}

	protected void setModelViewHandler(ModelViewHandler<?,?> handler) {
		T.call(this);

		NtroTask task = handler.getTask();

		mainTask.addSubTask(task);

		addPreviousTaskTo(task, ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
		addPreviousTaskTo(task, ModelLoader.class, MODEL_LOADER_TASK_ID);
	}

	protected void addModelViewSubViewHandler(Class<? extends NtroView> subViewClass, ModelViewSubViewHandler<?,?> handler) {
		T.call(this);

		NtroTask task = handler.getTask();

		mainTask.addSubTask(task);

		String subViewLoaderTaskId = Ntro.introspector().getSimpleNameForClass(subViewClass);

		ViewLoader subViewLoader = (ViewLoader) getTask().getSubTask(ViewLoader.class, subViewLoaderTaskId);
		handler.setSubViewLoader(subViewLoader);

		addPreviousTaskTo(task, ViewLoader.class, subViewLoaderTaskId);
		addPreviousTaskTo(task, ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
		addPreviousTaskTo(task, ModelLoader.class, MODEL_LOADER_TASK_ID);
	}


	protected <NT extends NtroTask> void addPreviousTaskTo(NtroTask nextTask, Class<NT> taskClass, String taskId) {
		T.call(this);

		NT previousTask = initTasks.getSubTask(taskClass, taskId);

		if(previousTask == null) {
			previousTask = mainTask.getSubTask(taskClass, taskId);
		}

		if(previousTask != null) {
			nextTask.addPreviousTask(previousTask);
		}
	}

	protected void setViewHandler(ViewHandler<?,?> handler) {
		T.call(this);

		handler.setController(this);

		mainTask.addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
	}


	protected void addViewMessageHandler(Class<? extends NtroMessage> messageClass, ViewMessageHandler<?,?> handler) {
		T.call(this);

		mainTask.addSubTask(handler.getTask());
		addPreviousTaskTo(handler.getTask(), ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);
	}

	public NtroView getView() {
		T.call(this);

		NtroView view = initTasks.getSubTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID).getView();

		return view;
	}

}
