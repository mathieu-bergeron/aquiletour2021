package ca.ntro.core.models.foreach;

public interface MapMapper<V extends Object> {

	V map(String key, V value) throws Break;

}
