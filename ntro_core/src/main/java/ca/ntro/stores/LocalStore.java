package ca.ntro.stores;

import ca.ntro.core.NtroUser;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

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

	public static void close() {
		T.call(LocalStore.class);

		try {

			instance.close();

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}

	}

	public static void registerThatUserObservesModel(NtroUser user, DocumentPath documentPath, NtroModel model) {
		T.call(LocalStore.class);

		try {

			instance.registerThatUserObservesModel(user, documentPath, model);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}
	}

	public static void save(NtroModel model) {
		T.call(LocalStore.class);

		try {

			instance.save(model);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(LocalStore.class) + " must be initialized", e);

		}
	}
}
