package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;


import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

import static ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID;
import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;
import static ca.ntro.core.mvc.Constants.SUB_MODEL_LOADER_TASK_ID;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;

public class ModelSubModelViewSubViewHandlerTask<M extends NtroModel, SM extends NtroModel, V extends NtroView> extends NtroTaskAsync {
	
	private ModelSubModelViewSubViewHandler<M,SM,V> handler;
	
	public ModelSubModelViewSubViewHandlerTask(ModelSubModelViewSubViewHandler<M,SM,V> handler) {
		T.call(this);

		this.handler = handler;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		ViewCreatorTask viewCreator = (ViewCreatorTask) getPreviousTask(ViewCreatorTask.class, VIEW_CREATOR_TASK_ID);

		ModelLoader modelLoader = (ModelLoader) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID);
		ModelLoader subModelLoader = (ModelLoader) getPreviousTask(ModelLoader.class, SUB_MODEL_LOADER_TASK_ID);
		
		MustNot.beNull(modelLoader);
		MustNot.beNull(subModelLoader);

		@SuppressWarnings("unchecked")
		M model = (M) modelLoader.getModel();
		MustNot.beNull(model);

		@SuppressWarnings("unchecked")
		SM subModel = (SM) subModelLoader.getModel();
		MustNot.beNull(subModel);

		MustNot.beNull(viewCreator);
		if(model == null) {
			System.out.println("modelLoader " + modelLoader.getClass().getSimpleName() );
		}

		@SuppressWarnings("unchecked")
		V view = (V) viewCreator.getView();
		
		MustNot.beNull(view);

		handler.handleImpl(view, model, subModel);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
