package ca.ntro.core.models.listeners;

import java.util.Map;

public interface MapObserver<V extends Object> extends ValueObserver<Map<String, V>>, ClearEntriesListener, EntryAddedListener<V> {
	
	void onEntryRemoved(String key, V value);

}
