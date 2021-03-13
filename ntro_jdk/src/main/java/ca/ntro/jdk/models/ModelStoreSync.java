package ca.ntro.jdk.models;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStore;

public class ModelStoreSync {

	private ModelStore modelStore;
	
	public ModelStoreSync(ModelStore modelStore) {
		this.modelStore = modelStore;
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder) {
		ModelLoader loader = modelStore.getLoader(modelClass, authToken, firstPathName, pathRemainder);
		loader.execute();
		
		M model = (M) loader.getModel();
		
		MustNot.beNull(model);

		return model;
	}

	public void save(NtroModel model) {
		T.call(this);

		modelStore.save(model);
	}
}
