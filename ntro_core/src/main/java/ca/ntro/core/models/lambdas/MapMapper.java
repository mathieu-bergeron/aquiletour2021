package ca.ntro.core.models.lambdas;


public interface MapMapper<V extends Object> {

	V map(String key, V value) throws Break;

}
