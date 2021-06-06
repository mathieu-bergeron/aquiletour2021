package ca.aquiletour.server.backend.login;


import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class InitializeSessionHandler extends BackendMessageHandler<InitializeSessionMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, InitializeSessionMessage message) throws BackendError {

		String authToken = null;
		if(message.getSessionUser() != null) {
			authToken = message.getSessionUser().getAuthToken();
		}

		if(authToken != null && 
				SessionManager.ifSessionExists(modelStore, authToken)) {

			SessionManager.updateExistingSession(modelStore, authToken);
			
		}else {

			SessionManager.createGuestSession(modelStore);

		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, InitializeSessionMessage message) {
		T.call(this);
	}

}
