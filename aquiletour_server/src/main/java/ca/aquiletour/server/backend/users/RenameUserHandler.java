package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.RenameUserMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class RenameUserHandler extends BackendMessageHandler<RenameUserMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RenameUserMessage message) throws BackendError {
		T.call(this);

	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RenameUserMessage message) throws BackendError {
		T.call(this);
	}

}
