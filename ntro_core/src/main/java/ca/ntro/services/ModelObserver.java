package ca.ntro.services;

import ca.ntro.core.models.NtroModel;

public interface ModelObserver {
	
	void onModelUpdate(NtroModel updatedModel);

}
