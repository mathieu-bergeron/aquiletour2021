package ca.aquiletour.server.backend.login;

import ca.aquiletour.core.messages.user.ItsNotMeMessage;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class ItsNotMeHandler extends BackendMessageHandler<ItsNotMeMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, ItsNotMeMessage message) {
		T.call(this);
		
		Ntro.currentSession().setUser(InitializeSessionHandler.createGuestSession(modelStore));

		if(message.getDelayedMessages().isEmpty()) {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			Ntro.messages().send(showLoginMenuMessage);
			
		}else {

			for(NtroMessage delayedMessage : message.getDelayedMessages()) {
				Ntro.messages().send(delayedMessage);
			}
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ItsNotMeMessage message) {
		T.call(this);
	}
}
