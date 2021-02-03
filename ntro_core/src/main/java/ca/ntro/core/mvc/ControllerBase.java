package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroViewModel;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapperImpl;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID;
import static ca.ntro.core.mvc.Constants.VIEW_RECEPTOR_TASK_ID;


abstract class ControllerBase extends TaskWrapperImpl {
	

	
	private Path path;

	protected abstract void initialize();

	public ControllerBase() {
		super(new ContainerTask());
		T.call(this);
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

	
	protected <C extends NtroController> void addSubController(Class<C> controllerClass, String controllerId) {
		T.call(this);
		
		Path pathRemainder = path.removePrefix(controllerId);
		
		C subController = ControllerFactory.createController(controllerClass, pathRemainder);
		
		getTask().addNextTask(subController.getTask());
	}
	
	protected void addSubViewLoader(ViewLoader viewLoader) {
		T.call(this);

	}

	protected void addMessageReceptor(Class<? extends NtroMessage> messageClass, MessageReceptor messageReceptor) {
		T.call(this);

	}

	protected void addMessageModel(Class<? extends NtroMessage> messageClass, MessageModel messageModel) {
		T.call(this);

	}
	
	protected void setViewLoader(ViewLoader viewLoader) {
		T.call(this);
		
		MustNot.beNull(viewLoader);
		
		// FIXME: it should be ok to reuse a viewLoader at DONE
		//        it simply means the view is already loaded
		viewLoader.reset();
		
		viewLoader.setTaskId(VIEW_LOADER_TASK_ID);
		getTask().addSubTask(viewLoader);
		
		addPreviousTaskTo(NtroViewModel.class, VIEW_MODEL_TASK_ID, viewLoader);
		addPreviousTaskTo(ViewReceptorTask.class, VIEW_RECEPTOR_TASK_ID, viewLoader);
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
		
		addPreviousTaskTo(NtroViewModel.class, VIEW_MODEL_TASK_ID, modelLoader);
	}

	protected void setViewModel(NtroViewModel viewModel) {
		T.call(this);
		
		getTask().addSubTask(viewModel);
		
		addPreviousTaskTo(viewModel, ViewLoader.class, VIEW_LOADER_TASK_ID);
		addPreviousTaskTo(viewModel, ModelLoader.class, MODEL_LOADER_TASK_ID);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(NtroTask nextTask, Class<NT> taskClass, String taskId) {
		T.call(this);
		
		NT previousTask = getTask().getSubTask(taskClass, taskId);
		
		if(previousTask != null) {
			nextTask.addPreviousTask(previousTask);
		}
	}

	protected void setViewReceptor(ViewReceptor<? extends ControllerBase, ? extends NtroView> viewReceptor) {
		T.call(this);
		
		viewReceptor.setController(this);

		getTask().addSubTask(viewReceptor.getTask());
		addPreviousTaskTo(viewReceptor.getTask(), ViewLoader.class, VIEW_LOADER_TASK_ID);
	}
}
