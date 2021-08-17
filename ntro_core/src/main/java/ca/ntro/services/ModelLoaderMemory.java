package ca.ntro.services;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;

public class ModelLoaderMemory extends ModelLoader {
	
	private NtroModel model;
	
	public ModelLoaderMemory(NtroModel model) {
		super();
		T.call(this);
		
		this.model = model;
	}
	

	public ModelLoaderMemory(ModelStore modelStore, DocumentPath documentPath) {
		super(modelStore, documentPath);
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		notifyTaskFinished();
	}

	@Override
	public NtroModel getModel() {
		T.call(this);

		return model;
	}

}
