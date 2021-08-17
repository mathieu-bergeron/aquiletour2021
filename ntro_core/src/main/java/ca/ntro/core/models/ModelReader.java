package ca.ntro.core.models;

import ca.ntro.backend.BackendError;

public interface ModelReader<M extends NtroModel> {
	
	void read(M existingModel) throws BackendError;

}
