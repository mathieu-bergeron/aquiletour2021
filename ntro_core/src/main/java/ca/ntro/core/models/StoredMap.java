package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class StoredMap<V extends Object> extends StoredProperty<Map<String, V>> {

	private List<MapObserver<V>> mapObservers = new ArrayList<>();

	public StoredMap(Map<String, V> value) {
		super();
		T.call(this);
		
		setValue(Ntro.collections().concurrentMap(value));
	}

	public StoredMap() {
		super();
		T.call(this);

		setValue(Ntro.collections().concurrentMap(new HashMap<>()));
	}

	public int size() {
		T.call(this);
		
		return getValue().size();
	}

	public void putEntry(String key, V value) {
		
		getValue().put(key, value);
		
		if(ifStoredConnected()) {

			modelStore().updateStoreConnectionsByPath(valuePath().getDocumentPath());

			List<Object> args = new ArrayList<>();
			args.add(key);
			args.add(value);
			modelStore().onValueMethodInvoked(valuePath(),"putEntry",args);

		}else {
			
			Log.warning("putEntry invoked while not connected to modelStore");
		}
		
		for(MapObserver<V> mapObserver : mapObservers) {
			mapObserver.onEntryAdded(key, value);
		}
	}

	public V valueOf(String key) {
		return getValue().get(key);
	}
	
	public void removeEntry(String key) {
		
		V value = getValue().get(key);
		getValue().remove(key);

		List<Object> args = new ArrayList<>();
		args.add(key);
		
		if(ifStoredConnected()) {

			modelStore().onValueMethodInvoked(valuePath(),"removeEntry",args);

		}else {

			Log.warning("removeEntry invoked while not connected to modelStore");
		}
		
		if(value != null) {
			for(MapObserver<V> mapObserver : mapObservers) {
				mapObserver.onEntryRemoved(key, value);
			}
		}
	}

	public void observe(MapObserver<V> mapObserver) {
		T.call(this);
		
		mapObservers.add(mapObserver);
		
		Map<String, V> map = getValue();
		
		if(map != null) {
			synchronized (map) {
				for(String key : map.keySet()) {
					V value = map.get(key);
					mapObserver.onEntryAdded(key, value);
				}
			}
		}
	}

	public boolean containsKey(String key) {
		T.call(this);

		return getValue().containsKey(key);
	}

	public void removeObservers() {
		T.call(this);
		
		this.mapObservers.clear();
	}

	public void clear() {
		T.call(this);
		
		getValue().clear();

		for(MapObserver<V> mapObserver : mapObservers) {
			mapObserver.onClearEntries();
		}
	}
	
	public void onEntryAdded(EntryAddedListener<V> listener) {
		T.call(this);
		
		this.observe(new MapObserver<V>() {
			@Override
			public void onValueChanged(Map<String, V> oldValue, Map<String, V> value) {
			}

			@Override
			public void onValue(Map<String, V> value) {
			}

			@Override
			public void onDeleted(Map<String, V> lastValue) {
			}

			@Override
			public void onClearEntries() {
			}

			@Override
			public void onEntryAdded(String key, V value) {
				listener.onEntryAdded(key, value);
			}

			@Override
			public void onEntryRemoved(String key, V value) {
			}
		});
	}
	
}
