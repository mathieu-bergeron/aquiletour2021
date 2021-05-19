package ca.ntro.core.models.functionnal;

public interface MapMapper<V extends Object> {

	V map(String key, V value) throws Break;

}
