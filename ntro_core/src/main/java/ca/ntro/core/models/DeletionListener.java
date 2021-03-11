package ca.ntro.core.models;

public interface DeletionListener<V extends Object> {
	
	void onDeleted(V lastValue);

}
