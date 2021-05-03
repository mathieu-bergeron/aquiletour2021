package ca.ntro.jsweet.services;

import ca.ntro.core.Constants;
import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.json.JsonLoaderJSweet;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.messages.ntro_messages.NtroGetModelMessage;
import ca.ntro.messages.ntro_messages.NtroInvokeValueMethodMessage;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;

import static def.es6.Globals.fetch;

import java.util.List;

public class NetworkStoreJSweet extends ModelStore {

	public NetworkStoreJSweet() {
		
		Ntro.backendService().handleMessageFromBackend(NtroInvokeValueMethodMessage.class, new MessageHandler<NtroInvokeValueMethodMessage>(){
			@Override
			public void handle(NtroInvokeValueMethodMessage message) {
				invokeValueMethod(message.getValuePath(), message.getMethodName(), message.getArgs());
			}
		});
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		T.call(this);
	}

	private String fullId(DocumentPath documentPath) {
		return documentPath.getCollection() + "/" + documentPath.getDocumentId();
	}

	@Override
	protected JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath) {
		T.call(this);
		
		String serviceUrl = Constants.MODELS_URL_PREFIX + "/";
		NtroGetModelMessage request = new NtroGetModelMessage();
		request.setUser(Ntro.currentUser());
		request.setDocumentPath(documentPath);
		request.registerTargetClass(targetClass);
		
		return new JsonLoaderJSweet(serviceUrl, request);
	}

	@Override
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage request) {
		T.call(this);

		return new JsonLoaderJSweet(serviceUrl, request);
	}

	@Override
	protected boolean ifModelExistsImpl(DocumentPath documentPath) {
		// XXX: always false. Cannot be known synchronously
		return false;
	}

    /*
	// JSWEET: does not compile
	// Object jsObject = Lang.await(fetchJsonObject(documentPath));
	private Promise<Object> fetchJsonObject(DocumentPath documentPath) {
		return fetch("/_B/" + fullId(documentPath)).then((Globals.FetchResponse response) -> {
			if (response.ok) {
				return response.json()
						.Catch((java.lang.Object error) -> {
							System.err.println("[NetworkStore] Erreur lors du chargement du modèle (JSON invalide)");
						});
			} else {
				return Promise.reject("[NetworkStore] Erreur lors du chargement du modèle (code non-200 du serveur)");
			}
		});
	}
	*/

	@Override
	public void saveDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);
		
		// XXX: not supported
	}

	@Override
	public void close() {
		// XXX: nothing to do
	}

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		// TODO Auto-generated method stub

	}



	@Override
	public <V> void setValue(ValuePath valuePath, V value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		// XXX: not supported
	}

	@Override
	protected void deleteDocument(DocumentPath documentPath) {
		// XXX: not supported
	}


}
