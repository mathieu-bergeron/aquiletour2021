package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.ToggleStudentModeMessage;
import ca.aquiletour.core.models.user.Teacher;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class ToggleStudentModeHandler extends BackendMessageHandler<ToggleStudentModeMessage>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ToggleStudentModeMessage message) throws BackendError {
		T.call(this);
		
		if(message.getUser() instanceof Teacher) {
			UserManager.toggleStudentMode(modelStore, message.getUser());
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ToggleStudentModeMessage message) {
		T.call(this);
	}
	
}
