package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.ToggleAdminModeMessage;
import ca.aquiletour.core.models.user.Admin;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class ToggleAdminModeHandler extends BackendMessageHandler<ToggleAdminModeMessage>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ToggleAdminModeMessage message) throws BackendError {
		T.call(this);
		
		if(message.getUser() instanceof Admin) {
			UserManager.toggleAdminMode(modelStore, message.getUser());
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ToggleAdminModeMessage message) {
		T.call(this);
	}
}
