package ca.ntro.core.models.listeners;

public interface DeletionListener<V extends Object> {
	
	void onDeleted(V lastValue);

}
