package ca.ntro.backend;

import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public abstract class BackendMessageHandler<M extends NtroMessage> {
	
	public abstract void handleNow(ModelStoreSync modelStore, M message) throws BackendMessageHandlerError ;
	public abstract void handleLater(ModelStoreSync modelStore, M message);

}