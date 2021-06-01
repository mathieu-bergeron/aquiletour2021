package ca.aquiletour.server.backend.login;


import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.models.user.User;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class InitializeSessionHandler extends BackendMessageHandler<InitializeSessionMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, InitializeSessionMessage message) {

		User sessionUser = null;
		User user = null;
		NtroSession session = null;
		
		sessionUser = message.getSessionUser();
		
		if(sessionUser != null) {

			session = SessionManager.getStoredSession(modelStore, sessionUser.getAuthToken());
		}

		if(session != null) {
			
			user = SessionManager.updateExistingSession(modelStore, session);
			Ntro.sessionService().registerCurrentSession(session);
			
		}else {

			user = SessionManager.createGuestSession(modelStore);
		}

		Ntro.currentSession().setUser(user);
	}


	@Override
	public void handleLater(ModelStoreSync modelStore, InitializeSessionMessage message) {
		T.call(this);
	}

}
