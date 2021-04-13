package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.random.SecureRandomString;
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

			session = getStoredSession(modelStore, sessionUser.getAuthToken());
		}

		if(session != null) {

			user = updateExistingSession(modelStore, session);
			
		}else {

			user = createGuestSession(modelStore);
		}

		Ntro.currentSession().setUser(user);
	}

	public static NtroSession getStoredSession(ModelStoreSync modelStore, String authToken) {
		T.call(InitializeSessionHandler.class);

		NtroSession session = null;
		
		if(modelStore.ifModelExists(NtroSession.class, "TODO", authToken)) {
			
			session = modelStore.getModel(NtroSession.class, "TODO", authToken);
		}

		return session;
	}

	private User updateExistingSession(ModelStoreSync modelStore, NtroSession session) {
		T.call(this);

		session.setTimeToLiveMiliseconds(session.getTimeToLiveMiliseconds() + 30 * 1000);  // TMP: 30 seconds extension
		
		User sessionUser = (User) session.getUser();
		User actualUser = null;
		
		if(sessionUser instanceof Guest 
				|| sessionUser instanceof TeacherGuest 
				|| sessionUser instanceof StudentGuest) {

			actualUser = sessionUser;

		}else {

			actualUser = updateSessionWithActualUser(modelStore, session, sessionUser);
		}
		
		return actualUser;
	}

	private User updateSessionWithActualUser(ModelStoreSync modelStore, NtroSession session, User oldSessionUser) {
		T.call(this);

		User actualUser = oldSessionUser;

		if(modelStore.ifModelExists(User.class, "TODO", oldSessionUser.getId())) {

			actualUser = modelStore.getModel(User.class, "TODO", oldSessionUser.getId());
			
			User sessionUser = actualUser.toSessionUser();
			sessionUser.setAuthToken(oldSessionUser.getAuthToken());
			session.setUser(sessionUser);
			modelStore.save(session);
			
			actualUser.setAuthToken(sessionUser.getAuthToken());
		}

		return actualUser;
	}

	public static User createGuestSession(ModelStoreSync modelStore) {
		T.call(InitializeSessionHandler.class);
		
		User user = new Guest();
		
		String authToken = SecureRandomString.generate();
		
		user.setId(authToken);
		user.setAuthToken(authToken);

		NtroSession session = modelStore.getModel(NtroSession.class, "TODO", authToken);

		session.setUser(user);
		session.setTimeToLiveMiliseconds(30 * 1000); // TMP: 30 seconds test
		
		modelStore.save(session);
			
		return user;
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, InitializeSessionMessage message) {
		T.call(this);
	}

}
