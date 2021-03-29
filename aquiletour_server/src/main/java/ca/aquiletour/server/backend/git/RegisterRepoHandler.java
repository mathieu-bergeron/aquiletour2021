package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class RegisterRepoHandler extends BackendMessageHandler<RegisterRepo> {

	@Override
	public void handleNow(ModelStoreSync modelStore, RegisterRepo message) {
		T.call(this);
		
		GitMessages.sendMessage(message);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, RegisterRepo message) {
		T.call(this);
		
	}

}
