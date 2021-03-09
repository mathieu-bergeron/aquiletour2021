package ca.ntro.core.services.stores;

import ca.ntro.core.Ntro;
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

	public static <M extends NtroModel> ModelLoader getLoader(Class<M> modelClass, String authToken, String firstPathName, String... pathRemainder) {
		
		ModelLoader modelLoader = null;

		try {

			modelLoader = instance.getLoaderImpl(modelClass, authToken, firstPathName, pathRemainder);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}

		return modelLoader;
	}

	public static void saveJsonString(DocumentPath path, String jsonString) {
		try {

			instance.saveJsonString(path, jsonString);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}
	}

	public static void updateModel(DocumentPath documentPath, NtroModel newModel) {
		try {

			instance.updateModel(documentPath, newModel);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}
	}

	public static void close() {
		T.call(LocalStore.class);

		try {

			instance.close();

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}

	}
}
