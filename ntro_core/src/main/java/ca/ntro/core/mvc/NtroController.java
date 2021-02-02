package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroViewModel;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.mvc.view.ViewReceptor;
import ca.ntro.core.mvc.view.ViewReceptorTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageReceptor;
import ca.ntro.messages.NtroMessage;

public abstract class NtroController implements TaskWrapper {
	
	private static final String VIEW_TASK_ID="view";
	private static final String MODEL_TASK_ID="model";
	private static final String VIEW_MODEL_TASK_ID="viewModel";
	private static final String VIEW_RECEPTOR_TASK_ID="viewReceptor";
	
	private Path path;
	private NtroTask mainTask = new ContainerTask();

	protected abstract void initialize();
	
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

	protected void setPath(Path path) {
		T.call(this);

		this.path = path;
	}

	
	protected <C extends NtroController> void addSubController(Class<C> controllerClass, String controllerId) {
		T.call(this);
		
		Path pathRemainder = path.removePrefix(controllerId);
		
		C subController = ControllerFactory.createController(controllerClass, pathRemainder);
		
		mainTask.addNextTask(subController.getTask());
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
		
		viewLoader.setTaskId(VIEW_TASK_ID);
		mainTask.addSubTask(viewLoader);
		
		addPreviousTaskTo(NtroViewModel.class, VIEW_MODEL_TASK_ID, viewLoader);
		addPreviousTaskTo(ViewReceptorTask.class, VIEW_RECEPTOR_TASK_ID, viewLoader);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(Class<NT> taskClass, String taskId, NtroTask previousTask) {
		T.call(this);

		NT task = mainTask.getSubTask(taskClass, taskId);
		
		if(task != null) {
			task.addPreviousTask(previousTask);
		}
	}
	
	protected void setModelLoader(ModelLoader modelLoader) {
		T.call(this);
		
		modelLoader.setTaskId(MODEL_TASK_ID);
		mainTask.addSubTask(modelLoader);
		
		addPreviousTaskTo(NtroViewModel.class, VIEW_MODEL_TASK_ID, modelLoader);
	}

	protected void setViewModel(NtroViewModel viewModel) {
		T.call(this);
		
		mainTask.addSubTask(viewModel);
		
		addPreviousTaskTo(viewModel, ViewLoader.class, VIEW_TASK_ID);
		addPreviousTaskTo(viewModel, ModelLoader.class, MODEL_TASK_ID);
	}

	private <NT extends NtroTask> void addPreviousTaskTo(NtroTask task, Class<NT> taskClass, String taskId) {
		T.call(this);
		
		NT previousTask = mainTask.getSubTask(taskClass, taskId);
		
		if(previousTask != null) {
			task.addPreviousTask(previousTask);
		}
	}

	protected void setViewReceptor(ViewReceptor<? extends NtroView> viewReceptor) {
		T.call(this);
		
		mainTask.addSubTask(viewReceptor.getTask());
		addPreviousTaskTo(viewReceptor.getTask(), ViewLoader.class, VIEW_TASK_ID);
	}

}
