package ca.ntro.core.mvc;

import ca.ntro.core.system.assertions.MustNot;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

import static ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;

public class ModelHandlerTask<M extends NtroModel> extends NtroTaskAsync {
	
	private ModelHandler<M> handler;
	
	public ModelHandlerTask(ModelHandler<M> handler) {
		T.call(this);

		this.handler = handler;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		@SuppressWarnings("unchecked")
		M model = (M) ((ModelLoader) getPreviousTask(ModelLoader.class, MODEL_LOADER_TASK_ID)).getModel();

		MustNot.beNull(model);

		handler.handleImpl(model);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
