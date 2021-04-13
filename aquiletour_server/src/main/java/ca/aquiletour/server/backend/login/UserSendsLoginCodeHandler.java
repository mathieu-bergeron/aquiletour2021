package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.server.RegisteredSockets;
import ca.aquiletour.server.backend.users.UserUpdater;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.SetUserNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.users.NtroSession;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		String loginCode = message.getLoginCode().replace(" ", "");
		String authToken = message.getUser().getAuthToken();
		String userId = message.getUser().getId();

		User userToRegister = null;

		NtroSession session = InitializeSessionHandler.getStoredSession(modelStore, authToken);
		SessionData sessionData = null;
		
		if(session != null) {
			sessionData = (SessionData) session.getSessionData();
		}
		
		if(sessionData != null 
				&& sessionData.getLoginCode().equals(loginCode)) {
			
			userToRegister = registerStudentOrTeacher(modelStore, authToken,  userId, session);
			
		}else {
			
			userToRegister = (User) message.getUser();
		}

		Ntro.currentSession().setUser(userToRegister);

		SetUserNtroMessage setUserNtroMessage = Ntro.messages().create(SetUserNtroMessage.class);
		setUserNtroMessage.setUser(userToRegister);
		RegisteredSockets.sendMessageToUser(userToRegister, setUserNtroMessage);
	}

	private User registerStudentOrTeacher(ModelStoreSync modelStore, String authToken, String userId, NtroSession session) {

		User existingUser = null;

		if(modelStore.ifModelExists(User.class, "TODO", userId)) {

			existingUser = modelStore.getModel(User.class, "TODO", userId);

		}else {
			
			if(session.getUser() instanceof TeacherGuest) {

				existingUser = new Teacher();

			} else if(session.getUser() instanceof StudentGuest) {

				existingUser = new Student();
			}
			
			existingUser.copyPublicInfomation((User) session.getUser());
			existingUser.setName(userId);
			existingUser.setId(userId);

			UserUpdater.addUser(modelStore, existingUser);
		}

		User sessionUser = existingUser.toSessionUser();
		
		sessionUser.setAuthToken(authToken);
		existingUser.setAuthToken(authToken);
		
		session.setUser(sessionUser);
		modelStore.save(session);

		return existingUser;
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		T.call(this);
	}

}
