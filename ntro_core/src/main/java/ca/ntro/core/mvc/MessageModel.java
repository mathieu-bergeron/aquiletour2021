package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.tasks.TaskWrapper;

public interface MessageModel extends TaskWrapper  {
	
	void onMessageReceived(NtroModel model, NtroMessage message);
}
