package ca.ntro.core.models.lambdas;

public interface MapIterator<V extends Object> {

	void on(String key, V value) throws Break;

}
