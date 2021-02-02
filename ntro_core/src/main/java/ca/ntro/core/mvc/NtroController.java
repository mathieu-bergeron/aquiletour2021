package ca.ntro.core.mvc;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroViewModel;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.web.Path;

public abstract class NtroController extends NtroTaskImpl {

	protected abstract ViewLoader createViewLoader(String lang);
	
	private static final String VIEW_TASK_ID="view";
	private static final String MODEL_TASK_ID="model";

	protected void initialize(Path path) {
		
		
	}
	
	protected void setViewLoader(ViewLoader viewLoader) {
		T.call(this);
		
		viewLoader.setTaskId(VIEW_TASK_ID);
		addSubTask(viewLoader);
		
		asViewModelPreviousTask(viewLoader);
	}

	private void asViewModelPreviousTask(NtroTask task) {
		T.call(this);

		NtroViewModel viewModel = getSubTask(NtroViewModel.class);
		
		if(viewModel != null) {
			viewModel.addPreviousTask(task);
		}
	}

	protected void setModelLoader(ModelLoader modelLoader) {
		T.call(this);
		
		modelLoader.setTaskId(MODEL_TASK_ID);
		addSubTask(modelLoader);
		
		asViewModelPreviousTask(modelLoader);
	}

	protected void setViewModel(NtroViewModel viewModel) {
		T.call(this);
		
		addSubTask(viewModel);
		
		addViewModelPreviousTask(viewModel, ViewLoader.class, VIEW_TASK_ID);
		addViewModelPreviousTask(viewModel, ModelLoader.class, MODEL_TASK_ID);
	}

	private <NT extends NtroTask> void addViewModelPreviousTask(NtroViewModel viewModel, Class<NT> taskClass, String taskId) {
		T.call(this);
		
		NT task = getSubTask(taskClass, taskId);
		
		if(task != null) {
			viewModel.addPreviousTask(task);
		}
	}
	

}
