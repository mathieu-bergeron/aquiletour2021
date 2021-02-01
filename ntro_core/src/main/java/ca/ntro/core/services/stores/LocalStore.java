package ca.ntro.core.services.stores;

import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class LocalStore {
	
	private static ModelStore instance;
	
	public static void initialize(ModelStore instance) {
		LocalStore.instance = instance;
	}

	public static <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String modelId) {
		
		ModelLoader modelLoader = null;
		
		try {
			
			modelLoader = instance.getLoaderImpl(modelClass, modelId);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return modelLoader;
	}

	public static void close() {
		T.call(LocalStore.class);

		try {
			
			instance.close();
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
	}

}