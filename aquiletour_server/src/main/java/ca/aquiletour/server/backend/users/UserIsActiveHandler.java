package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.UserIsActiveMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class UserIsActiveHandler extends BackendMessageHandler<UserIsActiveMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserIsActiveMessage message) throws BackendError {
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserIsActiveMessage message) throws BackendError {
		T.call(this);
		
		Log.info("[UserIsActive] " + message.getAuthToken());

	}

}
