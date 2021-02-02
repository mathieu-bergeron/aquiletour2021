package ca.ntro.core.mvc;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroViewModel;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.ContainerTask;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.MessageReceptor;
import ca.ntro.messages.NtroMessage;
import ca.ntro.web.Path;

public abstract class NtroController implements TaskWrapper {
	
	private static final String VIEW_TASK_ID="view";
	private static final String MODEL_TASK_ID="model";
	
	private NtroTask mainTask = new ContainerTask();

	protected abstract void initialize(Path path);
	
	public NtroTask getTask() {
		T.call(this);

		return mainTask;
	}
	
	protected void addSubController(NtroController subController) {
		T.call(this);
		
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
		
		asViewModelPreviousTask(viewLoader);
	}

	private void asViewModelPreviousTask(NtroTask task) {
		T.call(this);

		NtroViewModel viewModel = mainTask.getSubTask(NtroViewModel.class);
		
		if(viewModel != null) {
			viewModel.addPreviousTask(task);
		}
	}

	protected void setModelLoader(ModelLoader modelLoader) {
		T.call(this);
		
		modelLoader.setTaskId(MODEL_TASK_ID);
		mainTask.addSubTask(modelLoader);
		
		asViewModelPreviousTask(modelLoader);
	}

	protected void setViewModel(NtroViewModel viewModel) {
		T.call(this);
		
		mainTask.addSubTask(viewModel);
		
		addViewModelPreviousTask(viewModel, ViewLoader.class, VIEW_TASK_ID);
		addViewModelPreviousTask(viewModel, ModelLoader.class, MODEL_TASK_ID);
	}

	private <NT extends NtroTask> void addViewModelPreviousTask(NtroViewModel viewModel, Class<NT> taskClass, String taskId) {
		T.call(this);
		
		NT task = mainTask.getSubTask(taskClass, taskId);
		
		if(task != null) {
			viewModel.addPreviousTask(task);
		}
	}
	

}
