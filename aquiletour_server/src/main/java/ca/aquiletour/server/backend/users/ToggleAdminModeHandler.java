package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.ToggleAdminModeMessage;
import ca.aquiletour.core.models.user.Admin;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ToggleAdminModeHandler extends BackendMessageHandler<ToggleAdminModeMessage>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ToggleAdminModeMessage message) throws BackendMessageHandlerError {
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
