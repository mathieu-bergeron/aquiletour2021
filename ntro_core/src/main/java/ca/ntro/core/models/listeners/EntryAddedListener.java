package ca.ntro.core.models.listeners;

public interface EntryAddedListener<V extends Object> {

	void onEntryAdded(String key, V value);

}
