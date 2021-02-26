package ca.ntro.jdk.models;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.assertions.MustNot;

public class ModelStoreSync {

	private ModelStore modelStore;
	
	public ModelStoreSync(ModelStore modelStore) {
		this.modelStore = modelStore;
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroModel> M getModel(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder) {
		ModelLoader loader = modelStore.getLoaderImpl(modelClass, authToken, firstPathName, pathRemainder);
		loader.execute();
		
		M model = (M) loader.getModel();
		
		MustNot.beNull(model);

		return model;
	}
}
