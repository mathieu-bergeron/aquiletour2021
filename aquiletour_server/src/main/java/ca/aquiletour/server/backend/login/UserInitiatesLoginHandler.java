package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.user.UserInitiatesLoginMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.aquiletour.server.RegisteredSockets;
import ca.aquiletour.server.email.TestEmail;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.random.SecureRandomString;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroSetUserMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UserInitiatesLoginHandler extends BackendMessageHandler<UserInitiatesLoginMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserInitiatesLoginMessage message) {

		User user = message.getUser();
		String authToken = user.getAuthToken();
		String providedId = message.getProvidedId();
		User userToRegister = null;

		NtroSession session = InitializeSessionHandler.getStoredSession(modelStore, authToken);
		
		if(session != null) {

			userToRegister = registerStudentOrTeacherGuest(modelStore, authToken, providedId, session);

		}else {
			
			userToRegister = user;
		}

		Ntro.currentSession().setUser(userToRegister);
		
		NtroSetUserMessage setUserNtroMessage = Ntro.messages().create(NtroSetUserMessage.class);
		setUserNtroMessage.setUser(userToRegister);
		RegisteredSockets.sendMessageToUser(userToRegister, setUserNtroMessage);
		
		if(message.getDelayedMessages().isEmpty()) {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setMessageToUser("SVP entrer le code reçu par courriel");
			Ntro.messages().send(showLoginMenuMessage);
			
		}else {

			for(NtroMessage delayedMessage : message.getDelayedMessages()) {
				Ntro.messages().send(delayedMessage);
			}
		}
	}

	private User registerStudentOrTeacherGuest(ModelStoreSync modelStore, String authToken, String providedId, NtroSession session) {

		User userToRegister;
		
		if(isStudentId(providedId)) {
			
			userToRegister = new StudentGuest();
			
		}else {

			userToRegister = new TeacherGuest();
		}

		if(modelStore.ifModelExists(User.class, "TODO", providedId)) {

			User existingUser = modelStore.getModel(User.class, "TODO", providedId);
			userToRegister.copyPublicInfomation(existingUser);

		}else {

			userToRegister.setFirstname(providedId);
			userToRegister.setEmail(providedId + "@" + Constants.EMAIL_HOST);
		}

		userToRegister.setId(providedId);
		userToRegister.setAuthToken(authToken);
		
		String loginCode = SecureRandomString.generateLoginCode();

		Ntro.threadService().executeLater(new NtroTaskSync() {
			@Override
			protected void runTask() {
				T.call(this);

				T.values(loginCode, userToRegister.getFirstname(), userToRegister.getEmail());
				TestEmail.sendCode(loginCode, userToRegister.getFirstname(), userToRegister.getEmail());
			}

			@Override
			protected void onFailure(Exception e) {
			}
		});
		
		SessionData sessionData = new SessionData();
		sessionData.setLoginCode(loginCode.replace(" ", ""));

		session.setUser(userToRegister.toSessionUser());
		session.setSessionData(sessionData);
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

	@Override
	public void handleLater(ModelStoreSync modelStore, UserInitiatesLoginMessage message) {
		T.call(this);
	}
}
