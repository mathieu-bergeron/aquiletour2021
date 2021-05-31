package ca.ntro.core.models;

public interface ModelReducer<M extends NtroModel, ACC extends Object> {

	ACC reduce(M model, ACC accumulator);

}
