package ca.ntro.core.models;

import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.tasks.NtroTask;

public interface NtroViewModel extends NtroTask {

	void observeAndDisplay(NtroModel model, NtroView view);
	

}
