package ca.ntro.core.models;

public interface ModelReader<M extends NtroModel> {
	
	void read(M existingModel);

}
