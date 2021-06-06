package ca.aquiletour.server.backend.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.models.user_session.SessionsByUserId;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.registered_sockets.AuthTokenIterator;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.backend.BackendError;
import ca.ntro.core.Constants;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class SessionManager {

	public static boolean ifSessionExists(ModelStoreSync modelStore, String authToken) {
		T.call(SessionManager.class);
		
		return modelStore.ifModelExists(NtroSession.class, "admin", authToken);
	}

	public static User createGuestSession(ModelStoreSync modelStore) throws BackendError {
		T.call(SessionManager.class);
		
		User user = new Guest();
		
		String authToken = SecureRandomString.generate(Constants.RANDOM_STRING_DEFAULT_LENGTH);
		
		user.setId(authToken);
		user.setAuthToken(authToken);
		
		modelStore.createModel(NtroSession.class, "admin", authToken, session -> {

			session.setUser(user);
			session.setTimeToLiveMiliseconds(30 * 1000); // TMP: 30 seconds test

			SessionData sessionData = new SessionData();
			session.setSessionData(sessionData);

			Ntro.sessionService().registerCurrentSession(session);
		});
		
		return user;
	}

	public static void updateExistingSession(ModelStoreSync modelStore, String authToken) throws BackendError {
		T.call(SessionManager.class);
		
		modelStore.updateModel(NtroSession.class, "admin", authToken, session -> {

			session.setTimeToLiveMiliseconds(session.getTimeToLiveMiliseconds() + 30 * 1000);  // TMP: 30 seconds extension
			
			User sessionUser = (User) session.getUser();
			
			if(!(sessionUser instanceof Guest 
					|| sessionUser instanceof TeacherGuest 
					|| sessionUser instanceof StudentGuest)) {
				
				UserManager.updateUserWithStoredUserInfo(modelStore, sessionUser, sessionUser.getId());
			}
			
			Ntro.sessionService().registerCurrentSession(session);
		});
	}

	public static SessionData createSessionData(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(SessionManager.class);
		
		SessionData sessionData = new SessionData();
		
		CourseListManager.updateSessionData(modelStore, sessionData, user);

		return sessionData;
	}

	public static User createAuthenticatedUser(ModelStoreSync modelStore, 
			                                   String authToken, 
			                                   String userId,
			                                   User sessionUser) throws BackendError {
		T.call(SessionManager.class);

		User existingUser = null;

		if(modelStore.ifModelExists(User.class, "admin", userId)) {

			existingUser = modelStore.extractFromModel(User.class, "admin", userId, User.class, storedUser -> {
				return storedUser;
			});

		}else {

			User newUser = null;
			Set<String> adminRegistrationIds = UserManager.getAdminRegistrationIds(modelStore);
			
			if(sessionUser instanceof TeacherGuest && !adminRegistrationIds.contains(sessionUser.getId())) {

				newUser = new Teacher();

			} else if(sessionUser instanceof TeacherGuest && adminRegistrationIds.contains(sessionUser.getId())) {

				newUser = new Admin();

			} else if(sessionUser instanceof StudentGuest) {

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

		modelStore.updateModel(NtroSession.class, "admin", authToken, session -> {
			session.setUser(newSessionUser);
		});

		return existingUser;
	}

	public static void memorizeSessionByUserId(ModelStoreSync modelStore, 
			                                   String authToken, 
			                                   String userId) throws BackendError {

		T.call(SessionManager.class);

		if(modelStore.ifModelExists(SessionsByUserId.class, "admin", userId)) {
			modelStore.updateModel(SessionsByUserId.class, "admin", userId, new ModelUpdater<SessionsByUserId>() {
				@Override
				public void update(SessionsByUserId sessionsByUserId) throws BackendError {
					T.call(this);
					sessionsByUserId.addSession(authToken);
				}
			});
		}else {
			modelStore.createModel(SessionsByUserId.class, "admin", userId, new ModelInitializer<SessionsByUserId>() {
				@Override
				public void initialize(SessionsByUserId sessionsByUserId) {
					T.call(this);
					sessionsByUserId.addSession(authToken);
				}
			});
		}
	}

	private static void forgetSessionByUserId(ModelStoreSync modelStore, 
			                                  String authToken, 
			                                  String userId) throws BackendError {

		T.call(SessionManager.class);
		
		Optionnal<Boolean> shouldDeleteModel = new Optionnal<>(false);

		modelStore.updateModel(SessionsByUserId.class, "admin", userId, new ModelUpdater<SessionsByUserId>() {
			@Override
			public void update(SessionsByUserId sessionsByUserId) throws BackendError {
				T.call(this);
				sessionsByUserId.removeSession(authToken);
				
				if(sessionsByUserId.isEmpty()) {
					shouldDeleteModel.set(true);
				}
			}
		});
		
		try {
			if(shouldDeleteModel.get()) {
				modelStore.deleteModel(SessionsByUserId.class, "admin", userId);
			}
		} catch (EmptyOptionException e) {}
	}

	public static void deleteSession(ModelStoreSync modelStore, String authToken, User user) throws BackendError {
		T.call(SessionManager.class);

		modelStore.deleteModel(NtroSession.class, "admin", authToken);
		
		forgetSessionByUserId(modelStore, authToken, user.getId());
	}

	public static boolean isUserAuthenticated(ModelStoreSync modelStore, User user) {
		T.call(SessionManager.class);
		
		boolean isAuthenticated = false;
		
		String authToken = user.getAuthToken();
		
		if(authToken != null 
				&& !authToken.isEmpty()
				&& modelStore.ifModelExists(NtroSession.class, "admin", authToken)) {
			
			isAuthenticated = modelStore.extractFromModel(NtroSession.class, "admin", authToken, Boolean.class, session -> {

				boolean result = false;
				User storedUser = (User) session.getUser();
				
				if(storedUser != null 
						&& user != null
						&& storedUser.getClass().equals(user.getClass())) {

					String storedUserId = storedUser.getId();

					if(storedUserId != null
							&& storedUserId.equals(user.getId())
							&& !user.isGuest()
							&& !storedUser.isGuest()) {
						
						result = true;
					}
				}

				return result;
			});
		}

		return isAuthenticated;
	}
	
	public static void forEachUserSession(ModelStoreSync modelStore, String userId, AuthTokenIterator lambda) throws BackendError {
		T.call(SessionManager.class);
		
		List<String> authTokens = new ArrayList<>();
		
		modelStore.readModel(SessionsByUserId.class, "admin", userId, new ModelReader<SessionsByUserId>() {
			@Override
			public void read(SessionsByUserId sessionsByUserId) {
				T.call(this);
				
				for(String authToken: sessionsByUserId.getUserAuthTokens()) {
					authTokens.add(authToken);
				}
			}
		});
		
		for(String authToken : authTokens) {
			lambda.execute(authToken);
		}
	}

	public static void updateCurrentTasks(ModelStoreSync modelStore, 
			                              CoursePath coursePath, 
			                              List<CurrentTaskStudent> currentTasks, 
			                              String userId) throws BackendError {

		T.call(SessionManager.class);

		forEachUserSession(modelStore, userId, authToken -> {
			modelStore.updateModel(NtroSession.class, "admin", authToken, new ModelUpdater<NtroSession>() {
				@Override
				public void update(NtroSession session) throws BackendError {
					T.call(this);

					SessionData sessionData = (SessionData) session.getSessionData();
					sessionData.updateCurrentTasks(coursePath, currentTasks);

					NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
					updateSessionMessage.setSession(session);
					RegisteredSockets.sendMessageToSocket(authToken, updateSessionMessage);
				}
			});
		});
	}

	public static boolean ifLoginCodeValid(ModelStoreSync modelStore, String authToken, String loginCode) {
		T.call(SessionManager.class);

		return modelStore.extractFromModel(NtroSession.class, "admin", authToken, Boolean.class, session -> {
			
			boolean ifLoginCodeValid = false;
			
			if(session.getSessionData() instanceof SessionData) {
				
				SessionData sessionData = (SessionData) session.getSessionData();

				ifLoginCodeValid = loginCode.equals(sessionData.getLoginCode());
			}
			
			return ifLoginCodeValid;
		});
	}

	public static void updateSession(ModelStoreSync modelStore, String authToken, ModelUpdater<NtroSession> updater) throws BackendError {
		T.call(SessionManager.class);
		
		modelStore.updateModel(NtroSession.class, "admin", authToken, updater);
	}

}
