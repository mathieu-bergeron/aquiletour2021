package ca.ntro.jsweet.services;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.ModelStore;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ExternalUpdateListener;
import ca.ntro.stores.ValuePath;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.Storage;

import static def.dom.Globals.window;

import java.util.List;

public class LocalStoreJSweet extends ModelStore {
	
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
		
		return documentPath.getCollection() + "_" + documentPath.getDocumentId();
		
	}

	@Override
	protected JsonLoader getJsonLoader(Class<? extends NtroModel> targetClass, DocumentPath documentPath) {
		T.call(this);
		
		String jsonString = getJsonString(documentPath);
		
		if(jsonString == null) {

			jsonString = ModelStore.emptyModelString(documentPath);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(jsonString);

		return jsonLoader;
	}

	@Override
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		// XXX: not supported
		return null;
	}

	private String getJsonString(DocumentPath documentPath) {
		String fullId = fullId(documentPath);
		
		String jsonString = (String) localStorage.getItem(fullId);
		return jsonString;
	}

	@Override
	protected boolean ifModelExistsImpl(DocumentPath documentPath) {
		return getJsonString(documentPath) != null;
	}



	@Override
	public void saveDocument(DocumentPath documentPath, String jsonString) {
		T.call(this);

		String fullId = fullId(documentPath);
		
		localStorage.setItem(fullId, jsonString);
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
		String fullId = fullId(documentPath);
		
		localStorage.removeItem(fullId);
	}


}
