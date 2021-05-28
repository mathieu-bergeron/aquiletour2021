package ca.aquiletour.server.backend.login;

import java.util.Set;

import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.Constants;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.users.NtroSession;

public class SessionManager {

	public static User createGuestSession(ModelStoreSync modelStore) {
		T.call(SessionManager.class);
		
		User user = new Guest();
		
		String authToken = SecureRandomString.generate(Constants.RANDOM_STRING_DEFAULT_LENGTH);
		
		user.setId(authToken);
		user.setAuthToken(authToken);

		NtroSession session = modelStore.getModel(NtroSession.class, "admin", authToken);

		session.setUser(user);
		session.setTimeToLiveMiliseconds(30 * 1000); // TMP: 30 seconds test
		
		modelStore.save(session);
			
		return user;
	}

	public static User updateExistingSession(ModelStoreSync modelStore, NtroSession session) {
		T.call(SessionManager.class);

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


	public static NtroSession getStoredSession(ModelStoreSync modelStore, String authToken) {
		T.call(SessionManager.class);

		NtroSession session = null;
		
		if(modelStore.ifModelExists(NtroSession.class, "admin", authToken)) {

			session = modelStore.getModel(NtroSession.class, "admin", authToken);
		}

		return session;
	}

	public static User updateSessionWithActualUser(ModelStoreSync modelStore, NtroSession session, User oldSessionUser) {
		T.call(SessionManager.class);

		User actualUser = oldSessionUser;

		if(UserManager.ifStoredUserExists(modelStore, oldSessionUser)) {

			actualUser = UserManager.getStoredUser(modelStore, oldSessionUser);
			
			User sessionUser = actualUser.toSessionUser();
			sessionUser.setAuthToken(oldSessionUser.getAuthToken());
			session.setUser(sessionUser);
			modelStore.save(session);
			
			actualUser.setAuthToken(sessionUser.getAuthToken());
		}

		return actualUser;
	}

	public static User createAuthenticatedUser(ModelStoreSync modelStore, 
			                                   String authToken, 
			                                   String userId, 
			                                   NtroSession session) throws BackendError {
		T.call(SessionManager.class);

		User existingUser = null;
		User sessionUser = (User) session.getUser();

		if(modelStore.ifModelExists(User.class, "admin", userId)) {

			existingUser = modelStore.getModel(User.class, "admin", userId);

		}else {

			User newUser = null;
			Set<String> adminRegistrationIds = UserManager.getAdminRegistrationIds(modelStore);
			
			if(session.getUser() instanceof TeacherGuest && !adminRegistrationIds.contains(sessionUser.getId())) {

				newUser = new Teacher();

			} else if(session.getUser() instanceof TeacherGuest && adminRegistrationIds.contains(sessionUser.getId())) {

				newUser = new Admin();

			} else if(session.getUser() instanceof StudentGuest) {

				newUser = new Student();
			}
			
			newUser.copyPublicInfomation(sessionUser);
			newUser.setFirstname(sessionUser.getId());
			newUser.setId(userId);

			UserManager.createUser(modelStore, newUser);
			
			existingUser = newUser;
		}

		User newSessionUser = existingUser.toSessionUser();
		
		newSessionUser.setAuthToken(authToken);
		existingUser.setAuthToken(authToken);
		
		session.setUser(newSessionUser);
		modelStore.save(session);

		return existingUser;
	}

	public static void deleteSession(ModelStoreSync modelStore, String authToken) {
		T.call(SessionManager.class);

		modelStore.deleteModel(NtroSession.class, "admin", authToken);
	}

	public static boolean isUserAuthenticated(ModelStoreSync modelStore, User user) {
		T.call(SessionManager.class);
		
		boolean isAuthenticated = false;
		
		String authToken = user.getAuthToken();
		
		if(authToken != null 
				&& !authToken.isEmpty()
				&& modelStore.ifModelExists(NtroSession.class, "admin", authToken)) {
			
			NtroSession session = modelStore.getModel(NtroSession.class, "admin", authToken);
			
			User storedUser = (User) session.getUser();
			
			if(storedUser != null 
					&& user != null
					&& storedUser.getClass().equals(user.getClass())) {

				String storedUserId = storedUser.getId();

				if(storedUserId != null
						&& storedUserId.equals(user.getId())
						&& !user.isGuest()
						&& !storedUser.isGuest()) {
					
					isAuthenticated = true;
				}
			}
		}
		
		return isAuthenticated;
	}


}
