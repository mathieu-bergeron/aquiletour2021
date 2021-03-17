package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;

public class UserSendsLoginCodeHandler extends BackendMessageHandler<UserSendsLoginCodeMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, UserSendsLoginCodeMessage message) {
		String loginCode = message.getLoginCode();
	}

}
