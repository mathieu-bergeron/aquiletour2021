package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.models.users.User;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class UpdateUserInfoHandler extends BackendMessageHandler<UpdateUserInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateUserInfoMessage message) throws BackendMessageHandlerError {
		T.call(this);

		User user = (User) Ntro.currentSession().getUser();
		user.setName(message.getScreenName());
		user.setSurname("");
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateUserInfoMessage message) {
		T.call(this);

		UserUpdater.updateScreenName(modelStore, message.getScreenName(), message.getUser());
	}

}
