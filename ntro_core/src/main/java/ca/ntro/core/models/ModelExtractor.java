package ca.ntro.core.models;

public interface ModelExtractor<M extends NtroModel, R extends Object> {

	R extract(M model);

}
