package ca.ntro.core.models.functionnal;

public interface MapIterator<V extends Object> {

	void on(String key, V value) throws Break;

}
