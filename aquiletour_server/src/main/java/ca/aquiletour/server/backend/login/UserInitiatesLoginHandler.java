package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.User;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.Session;

public class UserInitiatesLoginHandler extends BackendMessageHandler<UserInitiatesLoginMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, UserInitiatesLoginMessage message) {

		User user = message.getUser();
		String authToken = user.getAuthToken();
		String providedId = message.getProvidedId();
		User userToRegister = null;
		
		Session session = AuthenticateSessionUserHandler.getStoredSession(modelStore, authToken);
		
		if(session != null) {

			User existingUser = modelStore.getModel(User.class, "TODO", providedId);
			
			userToRegister = new StudentGuest();

			userToRegister.copyPublicInfomation(existingUser);
			
			userToRegister.setId(providedId);
			
			userToRegister.setAuthToken(authToken);
			
			modelStore.replace(existingUser, userToRegister);
			
		}else {
			
			userToRegister = user;
		}

		Ntro.userService().registerCurrentUser(userToRegister);
	}



}
