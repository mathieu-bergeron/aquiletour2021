package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.RegisterRepoMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class RegisterRepoHandler extends BackendMessageHandler<RegisterRepoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RegisterRepoMessage message) {
		T.call(this);
		
		GitMessages.sendMessage(message);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RegisterRepoMessage message) {
		T.call(this);
		
	}

}
