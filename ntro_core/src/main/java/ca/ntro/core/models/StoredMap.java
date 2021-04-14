package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public abstract class StoredMap<V extends Object> extends StoredProperty<Map<String, V>> {

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
		
		List<Object> args = new ArrayList<>();
		args.add(key);
		args.add(value);
		
		modelStore().onValueMethodInvoked(valuePath(),"addEntry",args);
		
		
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
		
		modelStore().onValueMethodInvoked(valuePath(),"removeEntry",args);
		
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
}
