package ca.ntro.core.models;

import java.util.Map;

public interface MapObserver<V extends Object> extends ValueObserver<Map<String, V>> {
	
	void onEntryAdded(String key, V value);
	void onEntryRemoved(String key, V value);

}
