package ca.ntro.jsweet.services;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ExternalUpdateListener;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.json.JsonLoaderJSweet;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.Storage;

import static def.dom.Globals.window;

import static def.es6.Globals.fetch;

public class NetworkStoreJSweet extends ModelStore {

	// FIXME: replace by a server connection!!
	Storage localStorage = window.localStorage;

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		T.call(this);

		// XXX: called only when the storage is modified OUTSIDE our tab
		window.addEventListener("storage", new EventListener() {

			@Override
			public void $apply(Event evt) {
				T.call(this);

				updateListener.onExternalUpdate();
			}
		});

	}

	private String fullId(DocumentPath documentPath) {
		return documentPath.getCollection() + "/" + documentPath.getDocumentId();
	}

	@Override
	protected JsonLoader getJsonLoader(DocumentPath documentPath) {
		T.call(this);

		return new JsonLoaderJSweet(documentPath);
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
	public void saveJsonString(DocumentPath documentPath, String jsonString) {
		T.call(this);

		def.js.Object options = new def.js.Object();
		options.$set("method","POST");
		options.$set("body",jsonString);

		fetch("/_B/" + fullId(documentPath), options);
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
}
