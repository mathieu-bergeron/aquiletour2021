package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.system.trace.T;

public abstract class StoredMap<V extends Object> extends StoredProperty<Map<String, V>> {

	private List<MapObserver<V>> mapObservers = new ArrayList<>();

	public StoredMap(Map<String, V> value) {
		super(value);
		T.call(this);
	}

	public int size() {
		T.call(this);
		
		return getValue().size();
	}

	public void addEntry(String key, V value) {
		
		getValue().put(key, value);
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
}