package ca.ntro.jsweet.services;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.json.JsonLoaderMemory;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ExternalUpdateListener;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.trace.T;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.Storage;

import static def.dom.Globals.window;

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
		
		return documentPath.getCollection() + "_" + documentPath.getId();
		
	}

	@Override
	protected JsonLoader getJsonLoader(DocumentPath documentPath) {
		T.call(this);
		
		String fullId = fullId(documentPath);
		
		String jsonString = (String) localStorage.getItem(fullId);
		
		if(jsonString == null) {

			jsonString = ModelStore.emptyModelString(documentPath);
		}
		
		JsonLoader jsonLoader = new JsonLoaderMemory(documentPath, jsonString);

		return jsonLoader;
	}




	@Override
	public void saveJsonString(DocumentPath documentPath, String jsonString) {
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
}
