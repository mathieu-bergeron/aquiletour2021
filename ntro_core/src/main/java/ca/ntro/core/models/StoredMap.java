package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.models.lambdas.MapIterator;
import ca.ntro.core.models.lambdas.MapMapper;
import ca.ntro.core.models.lambdas.MapReducer;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class StoredMap<V extends Object> extends StoredProperty<Map<String, V>> implements NtroCloneable<StoredMap<V>> {

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

		synchronized (getValue()) {
			getValue().put(key, value);
		}
		
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
		T.call(this);
		
		V value = null;
		
		synchronized (getValue()) {
			value = getValue().get(key);
		}
		
		return value;
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
		
		boolean contains = false;
		
		synchronized (getValue()) {
			contains = getValue().containsKey(key);
		}

		return contains;
	}

	public void removeObservers() {
		T.call(this);
		
		this.mapObservers.clear();
	}

	public void clear() {
		T.call(this);
		
		synchronized (getValue()) {
			getValue().clear();
		}
		
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
	
	@Override
	public StoredMap<V> cloneModelValue() throws CloneNotSupportedException {
		T.call(this);
		
		StoredMap<V> clone = new StoredMap<>();
		
		for(Map.Entry<String, V> entry : getValue().entrySet()) {
			
			V value = entry.getValue();
			
			if(value instanceof NtroCloneable) {
				
				clone.putEntry(entry.getKey(), ((NtroCloneable<V>)value).cloneModelValue());
				
			}else {

				throw new CloneNotSupportedException("To clone a Map, its values must implement NtroCloneable");
			}
		}
		
		return clone;
	}

	public <R extends Object> R reduceTo(Class<R> valueClass, R accumulator, MapReducer<R,V> reducer) {
		T.call(this);
		
		synchronized (getValue()) {
			for(Map.Entry<String, V> entry : getValue().entrySet()) {
				try {

					accumulator = reducer.reduce(entry.getKey(), entry.getValue(), accumulator);

				}catch(Break b) {
					break;
				}
			}
		}

		return accumulator;
	}
	
	public void forEachEntry(MapIterator<V> lambda) {
		T.call(this);

		synchronized (getValue()) {
			for(Map.Entry<String, V> entry : getValue().entrySet()) {
				try {

					lambda.on(entry.getKey(), entry.getValue());

				}catch(Break b) {
					break;
				}
			}
		}
	}

	public void map(MapMapper<V> lambda) {
		T.call(this);
		
		Map<String, V> toUpdate = new HashMap<>();

		synchronized (getValue()) {
			for(Map.Entry<String, V> entry : getValue().entrySet()) {
				try {

					V newValue = lambda.map(entry.getKey(), entry.getValue());
					if(!entry.getValue().equals(newValue)) {
						toUpdate.put(entry.getKey(), newValue);
					}

				}catch(Break b) {
					break;
				}
			}
		}

		for(Map.Entry<String, V> entryUpdate : toUpdate.entrySet()) {
			putEntry(entryUpdate.getKey(), entryUpdate.getValue());
		}
	}
}
