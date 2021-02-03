package ca.ntro.core.mvc;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.tasks.TaskWrapper;
import ca.ntro.messages.NtroMessage;

public interface ModelMessageHandler extends TaskWrapper  {
	
	void onMessageReceived(NtroModel model, NtroMessage message);
}
