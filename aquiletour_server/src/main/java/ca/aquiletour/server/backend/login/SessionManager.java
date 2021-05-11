package ca.aquiletour.server.backend.login;

import java.util.List;

import ca.aquiletour.core.models.users.Admin;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.backend.queue.QueueUpdater;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class SessionManager {

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

		if(modelStore.ifModelExists(User.class, "admin", oldSessionUser.getId())) {

			actualUser = modelStore.getModel(User.class, "admin", oldSessionUser.getId());
			
			User sessionUser = actualUser.toSessionUser();
			sessionUser.setAuthToken(oldSessionUser.getAuthToken());
			session.setUser(sessionUser);
			modelStore.save(session);
			
			actualUser.setAuthToken(sessionUser.getAuthToken());
		}

		return actualUser;
	}

	public static User createAuthenticatedUser(ModelStoreSync modelStore, String authToken, String userId, NtroSession session) {
		T.call(SessionManager.class);

		User existingUser = null;

		if(modelStore.ifModelExists(User.class, "admin", userId)) {

			existingUser = modelStore.getModel(User.class, "admin", userId);

		}else {

			User newUser = null;
			List<String> adminIds = ((AquiletourConfig)Ntro.config()).getAdminIds();
			
			if(session.getUser() instanceof TeacherGuest && !adminIds.contains(session.getUser().getId())) {

				newUser = new Teacher();

			} else if(session.getUser() instanceof TeacherGuest && adminIds.contains(session.getUser().getId())) {

				newUser = new Admin();

			} else if(session.getUser() instanceof StudentGuest) {

				newUser = new Student();
			}
			
			newUser.copyPublicInfomation((User) session.getUser());
			newUser.setFirstname(userId);
			newUser.setId(userId);

			UserManager.addUser(modelStore, newUser);
			
			if(newUser instanceof Teacher) {
				QueueUpdater.createQueue(modelStore, newUser.getId(), newUser.getId());
			}
			
			existingUser = newUser;
		}

		User sessionUser = existingUser.toSessionUser();
		
		sessionUser.setAuthToken(authToken);
		existingUser.setAuthToken(authToken);
		
		session.setUser(sessionUser);
		modelStore.save(session);

		return existingUser;
	}

	public static void deleteSession(ModelStoreSync modelStore, String authToken) {
		T.call(SessionManager.class);

		modelStore.deleteModel(NtroSession.class, "admin", authToken);
	}


}
