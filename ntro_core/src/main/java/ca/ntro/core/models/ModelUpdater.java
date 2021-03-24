package ca.ntro.core.models;

public interface ModelUpdater<M extends NtroModel> {
	
	void update(M existingModel);

}
