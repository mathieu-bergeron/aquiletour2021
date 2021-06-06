package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.models.user.User;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class UpdateUserInfoHandler extends BackendMessageHandler<UpdateUserInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateUserInfoMessage message) throws BackendError {
		T.call(this);

		NtroSession session = Ntro.currentSession();
		if(session.getUser() instanceof User) {
			User user = (User) session.getUser();
			user.setFirstname(message.getScreenName());
			user.setLastname("");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateUserInfoMessage message) throws BackendError {
		T.call(this);

		UserManager.updateScreenName(modelStore, message.getScreenName(), message.getUser());
	}

}
