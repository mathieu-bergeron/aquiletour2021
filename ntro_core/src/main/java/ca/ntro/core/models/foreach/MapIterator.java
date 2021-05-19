package ca.ntro.core.models.foreach;

public interface MapIterator<V extends Object> {

	void on(String key, V value) throws Break;

}
