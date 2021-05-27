package ca.ntro.core.models;

public interface NtroCloneable<V extends Object> {

	V cloneModelValue() throws CloneNotSupportedException;

}
