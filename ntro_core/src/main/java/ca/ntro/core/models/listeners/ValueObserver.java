package ca.ntro.core.models.listeners;

public interface ValueObserver<V extends Object> extends ValueListener<V>, DeletionListener<V> {
	
	void onValueChanged(V oldValue, V value);

}
