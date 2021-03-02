package ca.ntro.core.models.properties.observable.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.system.trace.T;

public abstract class ObservableMap<V extends Object> extends ObservableProperty<Map<String, V>> {

	private List<MapObserver<V>> mapObservers = new ArrayList<>();

	public ObservableMap(Map<String, V> value) {
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
	
	
	
	@Override
	public JsonObject toJsonObject() {
		T.call(this);
		
		JsonObject result = JsonParser.jsonObject();
		result.setTypeName(this.getClass().getName());

		Map<String,V> map = getValue();
		
		Map<String,Object> jsonMap = new HashMap<>();
		
		for(Map.Entry<String, V> entry : map.entrySet()) {

			Object jsonValue = entry.getValue();
			
			// FIXME: this should be Ntro.introspector.ifItImplements(NtroModelValue.class)
			if(getValueType().equals(NtroModelValue.class)) {
				jsonValue = ((NtroModelValue) jsonValue).toJsonObject().toMap();
			}
			
			jsonMap.put(entry.getKey(), jsonValue);
		}
		
		result.put("value", jsonMap);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadFromJsonObject(JsonObject jsonObject) {
		
		Map<String,?> jsonMap = (Map<String,?>) jsonObject.get("value");

		for(Map.Entry<String, ?> entry : jsonMap.entrySet()) {
			
			Object entryValue = Ntro.introspector().buildValueForType(getValueType(), entry.getValue());
			
			getValue().put(entry.getKey(), (V) entryValue);
		}
	}
	
}
