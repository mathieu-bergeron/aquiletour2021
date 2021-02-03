package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageReceptor;
import ca.ntro.messages.NtroMessage;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_HANDLER_TASK_ID;


abstract class NtroAbstractController implements TaskWrapper {
	
	private NtroTask mainTask = new ContainerTask();
	private Path path;

	protected abstract void initialize();
	protected abstract void onFailure(Exception e);

	public NtroAbstractController() {
		T.call(this);
		
		mainTask.setTaskId(this.getClass().getSimpleName());
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

	protected void setPath(Path path) {
		T.call(this);

		this.path = path;
	}

	
	protected <C extends NtroController<?>> void addSubController(Class<C> controllerClass, String controllerId) {
		T.call(this);
		
		if(path.startsWith(controllerId) || path.startsWith("*")) {
			Path pathRemainder = path.removePrefix(controllerId);

			C subController = ControllerFactory.createController(controllerClass, pathRemainder, this);
			
			getTask().addNextTask(subController.getTask());

		}else {
			
			System.err.println("[WARNING]: subController not added: " + controllerId);

		}

	}
	
	protected void addSubViewLoader(ViewLoader viewLoader) {
		T.call(this);

	}

	protected void addMessageHandler(Class<? extends NtroMessage> messageClass, MessageReceptor messageReceptor) {
		T.call(this);

	}

	protected void addModelMessageHandler(Class<? extends NtroMessage> messageClass, ModelMessageHandler<?,?> messageModel) {
		T.call(this);
		
	}
	
	protected void setViewLoader(ViewLoader viewLoader) {
		T.call(this);
		
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
		
		addPreviousTaskTo(ViewModelHandlerTask.class, VIEW_MODEL_TASK_ID, viewCreator);
		addPreviousTaskTo(ViewHandlerTask.class, VIEW_HANDLER_TASK_ID, viewCreator);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(Class<NT> taskClass, String taskId, NtroTask previousTask) {
		T.call(this);

		NT task = getTask().getSubTask(taskClass, taskId);
		
		if(task != null) {
			task.addPreviousTask(previousTask);
		}
	}
	
	protected void setModelLoader(ModelLoader modelLoader) {
		T.call(this);
		
		modelLoader.setTaskId(MODEL_LOADER_TASK_ID);
		getTask().addSubTask(modelLoader);
		
		addPreviousTaskTo(ViewModelHandlerTask.class, VIEW_MODEL_TASK_ID, modelLoader);
	}

	protected void setViewModelHandler(ViewModelHandler<?,?> handler) {
		T.call(this);
		
		NtroTask task = handler.getTask();
		
		getTask().addSubTask(task);
		
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

	NtroView getView() {
		T.call(this);
		
		NtroView view = getTask().getSubTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID).getView();
		
		return view;
	}
}
