package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.AuthenticateSessionUserMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;
import jsweet.util.StringTypes.ol;

public class AuthenticateSessionUserHandler extends BackendMessageHandler<AuthenticateSessionUserMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AuthenticateSessionUserMessage message) {

		User sessionUser = null;
		User user = null;
		Session session = null;
		
		sessionUser = message.getSessionUser();
		
		if(sessionUser != null) {

			session = getStoredSession(modelStore, sessionUser.getAuthToken());
		}

		if(session != null) {

			user = updateExistingSession(modelStore, session);
			
		}else {

			user = createGuestSession(modelStore);
		}

		Ntro.userService().registerCurrentUser(user);
	}

	public static Session getStoredSession(ModelStoreSync modelStore, String authToken) {
		T.call(AuthenticateSessionUserHandler.class);

		Session session = null;
		
		if(modelStore.ifModelExists(Session.class, "TODO", authToken)) {
			
			session = modelStore.getModel(Session.class, "TODO", authToken);
		}

		return session;
	}

	private User updateExistingSession(ModelStoreSync modelStore, Session session) {
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

	private User updateSessionWithActualUser(ModelStoreSync modelStore, Session session, User oldSessionUser) {
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
		T.call(AuthenticateSessionUserHandler.class);
		
		User user = new Guest();
		
		String authToken = SecureRandomString.generate();
		
		user.setId(authToken);
		user.setAuthToken(authToken);

		Session session = modelStore.getModel(Session.class, "TODO", authToken);

		session.setUser(user);
		session.setTimeToLiveMiliseconds(30 * 1000); // TMP: 30 seconds test
		
		modelStore.save(session);
			
		return user;
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AuthenticateSessionUserMessage message) {
		T.call(this);
	}

}
