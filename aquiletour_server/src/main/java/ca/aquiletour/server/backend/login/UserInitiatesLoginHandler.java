package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.UserInitiatesLoginMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.aquiletour.core.utils.ValidationError;
import ca.aquiletour.core.utils.Validator;
import ca.aquiletour.server.backend.users.UserManager;
import ca.aquiletour.server.email.SendEmail;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UserInitiatesLoginHandler extends BackendMessageHandler<UserInitiatesLoginMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserInitiatesLoginMessage message) throws BackendError {
		T.call(this);

		User sessionUser = message.getUser();
		String authToken = sessionUser.getAuthToken();
		String userId = message.getRegistrationId();

		userId = Validator.deAccent(userId);
		
		try {

			Validator.mustBeValidId(userId);
			
		}catch(ValidationError e) {
			throw new BackendError(e.getMessage() + ". SVP entrer votre DA ou le début de votre courriel (sans le @)");
		}
		
		userId = User.normalizeUserId(userId);

		sessionUser = registerStudentOrTeacherGuest(modelStore, authToken, userId);

		Ntro.currentSession().setUser(sessionUser);
		
		NtroUpdateSessionMessage updateSessionMessage = Ntro.messages().create(NtroUpdateSessionMessage.class);
		updateSessionMessage.setSession(Ntro.currentSession());
		RegisteredSocketsSockJS.sendMessageToSockets(authToken, updateSessionMessage);
		
		if(message.getDelayedMessages().isEmpty() && sessionUser.getHasPassword()) {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setMessageToUser("SVP entrer votre mot de passe");
			Ntro.messages().send(showLoginMenuMessage);

		} else if(message.getDelayedMessages().isEmpty() && !sessionUser.getHasPassword()) {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setMessageToUser("SVP valider votre identité");
			Ntro.messages().send(showLoginMenuMessage);
			
		}else {

			for(NtroMessage delayedMessage : message.getDelayedMessages()) {
				Ntro.messages().send(delayedMessage);
			}
		}
	}

	private User registerStudentOrTeacherGuest(ModelStoreSync modelStore, 
			                                   String authToken, 
			                                   String userId) throws BackendError {
		T.call(this);
		
		
		SessionManager.memorizeSessionByUserId(modelStore, authToken, userId);
		
		User userToRegister;

		if(UserManager.isStudentId(userId)) {
			
			userToRegister = UserManager.createGuestUser(modelStore, StudentGuest.class, userId);
			
		}else {

			userToRegister = UserManager.createGuestUser(modelStore, TeacherGuest.class, userId);
		}

		userToRegister.setAuthToken(authToken);
		
		SessionData sessionData = SessionManager.createSessionData(modelStore, userToRegister);

		if(!userToRegister.hasPassword()) {
			String loginCode = sendLoginCode(userToRegister);
			sessionData.setLoginCode(loginCode);
		}
		
		SessionManager.updateSession(modelStore, authToken, session -> {
			session.setUser(userToRegister.toSessionUser());
			session.setSessionData(sessionData);
			
		});

		return userToRegister;
	}

	public static String sendLoginCode(User userToRegister) {
		T.call(UserInitiatesLoginHandler.class);

		String loginCode = SecureRandomString.generateLoginCode();

		Ntro.threadService().executeLater(new NtroTaskSync() {
			@Override
			protected void runTask() {
				T.call(this);

				Log.info("CODE: " + loginCode);
				SendEmail.sendCode(loginCode, userToRegister.getFirstname(), userToRegister.getEmail());
			}

			@Override
			protected void onFailure(Exception e) {
			}
		});

		return loginCode.replace(" ", "");
	}


	@Override
	public void handleLater(ModelStoreSync modelStore, UserInitiatesLoginMessage message) {
		T.call(this);
	}
}
