package ca.ntro.core.models;

public interface ModelInitializer<M extends NtroModel> {
	
	void initialize(M newModel);

}
