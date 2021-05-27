package ca.ntro.core.models;

import ca.ntro.backend.BackendError;

public interface ModelUpdater<M extends NtroModel> {
	
	void update(M existingModel) throws BackendError;

}
