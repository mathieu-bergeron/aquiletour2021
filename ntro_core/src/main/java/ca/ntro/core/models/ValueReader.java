package ca.ntro.core.models;

public interface ValueReader<V extends Object> {

	void read(V modelValue);

}
