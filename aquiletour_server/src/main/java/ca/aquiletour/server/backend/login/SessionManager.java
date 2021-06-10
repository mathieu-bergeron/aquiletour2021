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
import ca.ntro.users.NtroUser;

public class SessionManager {

	public static boolean ifSessionExists(ModelStoreSync modelStore, String authToken) {
		T.call(SessionManager.class);
		
		return modelStore.ifModelExists(NtroSession.class, "admin", authToken);
	}

	public static void createSession(ModelStoreSync modelStore, String authToken, ModelInitializer<NtroSession> initializer) throws BackendError {
		T.call(SessionManager.class);
		
		modelStore.createModel(NtroSession.class, "admin", authToken, session -> {
			
			initializer.initialize(session);
			
			Ntro.sessionService().registerCurrentSession(session);
		});
	}

	public static void updateSession(ModelStoreSync modelStore, String authToken, ModelUpdater<NtroSession> updater) throws BackendError {
		T.call(SessionManager.class);
		
		modelStore.updateModel(NtroSession.class, "admin", authToken, session -> {
			
			updater.update(session);
			
			Ntro.sessionService().registerCurrentSession(session);
		});
	}

	public static User createGuestSession(ModelStoreSync modelStore) throws BackendError {
		T.call(SessionManager.class);
		
		User user = createGuestUser(null);
		
		createSession(modelStore, user.getAuthToken(), session -> {
			T.call(SessionManager.class);

			session.setUser(user);
			
			if(Ntro.config().isProd()) {
				
				session.setTimeToLiveMiliseconds(60*60*24*4);  // 4 months

			}else {

				session.setTimeToLiveMiliseconds(30);
			}

			SessionData sessionData = new SessionData();
			session.setSessionData(sessionData);
		});
		
		return user;
	}

	public static User createGuestUser(String authToken) throws BackendError {
		T.call(SessionManager.class);

		User user = new Guest();
		
		if(authToken == null) {
			authToken = SecureRandomString.generate(Constants.RANDOM_STRING_DEFAULT_LENGTH);
		}

		user.setId(authToken);
		user.setAuthToken(authToken);
			
		return user;
	}

	public static void updateExistingSession(ModelStoreSync modelStore, String authToken) throws BackendError {
		T.call(SessionManager.class);
		
		updateSession(modelStore, authToken, session -> {

			session.setTimeToLiveMiliseconds(session.getTimeToLiveMiliseconds() + 30 * 1000);  // TMP: 30 seconds extension
			
			NtroUser sessionUser = session.getUser();

			if(shouldCreateGuestUser(sessionUser)) {

				session.setUser(createGuestUser(sessionUser.getAuthToken()));

			} else if(shouldUpdateSessionUser(sessionUser)) {
				
				UserManager.updateUserWithStoredUserInfo(modelStore, (User) sessionUser, sessionUser.getId());
			}
		});
	}

	private static boolean shouldCreateGuestUser(NtroUser sessionUser) {
		T.call(SessionManager.class);

		return !(sessionUser instanceof User);
	}

	private static boolean shouldUpdateSessionUser(NtroUser sessionUser) {
		T.call(SessionManager.class);

		return sessionUser instanceof User 
				&& !(sessionUser instanceof Guest 
						|| sessionUser instanceof TeacherGuest 
						|| sessionUser instanceof StudentGuest);
	}

	public static SessionData createSessionData(ModelStoreSync modelStore, User user) throws BackendError {
		T.call(SessionManager.class);
		
		SessionData sessionData = new SessionData();
		
		CourseListManager.updateSessionData(modelStore, sessionData, user);

		return sessionData;
	}

	public static void createAuthenticatedUser(ModelStoreSync modelStore, 
			                                   String authToken, 
			                                   String userId,
			                                   User sessionUser) throws BackendError {
		T.call(SessionManager.class);

		User authenticatedUser;
		Set<String> adminRegistrationIds = UserManager.getAdminRegistrationIds(modelStore);
		
		if(sessionUser instanceof TeacherGuest && !adminRegistrationIds.contains(sessionUser.getId())) {

			authenticatedUser = new Teacher();

		} else if(sessionUser instanceof TeacherGuest && adminRegistrationIds.contains(sessionUser.getId())) {

			authenticatedUser = new Admin();

		} else if(sessionUser instanceof StudentGuest) {

			authenticatedUser = new Student();

		}else {

			authenticatedUser = new Student();
		}
		
		authenticatedUser.copyPublicInfomation(sessionUser);
		authenticatedUser.setAuthToken(sessionUser.getAuthToken());
		authenticatedUser.setFirstname(sessionUser.getId());
		authenticatedUser.setId(userId);
		
		if(!UserManager.ifStoredUserExists(modelStore, authenticatedUser)) {

			UserManager.createUser(modelStore, authenticatedUser);

		}else {

			UserManager.updateUserWithStoredUserInfo(modelStore, authenticatedUser, userId);
		}

		updateSession(modelStore, authToken, session -> {
			session.setUser(authenticatedUser.toSessionUser());
		});
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

	public static void updateUser(ModelStoreSync modelStore, 
			                      String userId) throws BackendError {

		T.call(SessionManager.class);
		
		modelStore.readModel(User.class, "admin", userId, user -> {
			T.call(SessionManager.class);

			SessionManager.updateSession(modelStore, user.getAuthToken(), session -> {
				T.call(SessionManager.class);

				session.setUser(user.toSessionUser());
			});
		});
	}

}
