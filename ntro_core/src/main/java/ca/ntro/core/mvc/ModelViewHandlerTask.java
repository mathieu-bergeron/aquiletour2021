package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;

public class ModelViewHandlerTask<M extends NtroModel, V extends NtroView> extends NtroTaskImpl {
	
	private ModelViewHandler<M,V> handler;
	
	public ModelViewHandlerTask(ModelViewHandler<M,V> handler) {
		T.call(this);

		this.handler = handler;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		ViewCreatorTask viewCreator = (ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);

		@SuppressWarnings("unchecked")
		M model = (M) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID).getModel();

		MustNot.beNull(viewCreator);
		MustNot.beNull(model);

		@SuppressWarnings("unchecked")
		V view = (V) viewCreator.getView();
		
		MustNot.beNull(view);

		handler.handleImpl(view, model);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
