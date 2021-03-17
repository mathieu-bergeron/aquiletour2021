package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;

public class UserInitiatesLoginHandler extends BackendMessageHandler<UserInitiatesLoginMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, UserInitiatesLoginMessage message) {
		

		User user = message.getUser();
		String authToken = user.getAuthToken();
		String providedId = message.getProvidedId();
		User userToRegister = null;

		T.values("providedId", providedId);
		
		Session session = AuthenticateSessionUserHandler.getStoredSession(modelStore, authToken);
		
		if(session != null) {

			userToRegister = registerStudentOrTeacherGuest(modelStore, authToken, providedId, session);

		}else {
			
			userToRegister = user;
		}

		Ntro.userService().registerCurrentUser(userToRegister);
	}

	private User registerStudentOrTeacherGuest(ModelStoreSync modelStore, String authToken, String providedId, Session session) {

		User userToRegister;

		User existingUser = modelStore.getModel(User.class, "TODO", providedId);
		
		if(isStudentId(providedId)) {
			
			userToRegister = new StudentGuest();
			
		}else {

			userToRegister = new TeacherGuest();
			
		}

		userToRegister.copyPublicInfomation(existingUser);
		
		userToRegister.setId(providedId);
		userToRegister.setAuthToken(authToken);
		
		session.setUser(userToRegister.toSessionUser());
		modelStore.save(session);

		return userToRegister;
	}

	private boolean isStudentId(String providedId) {
		boolean isStudentId = false;

		if(providedId.matches("^\\d{7}$")) {
			isStudentId = true;
		}

		return isStudentId;
	}



}
