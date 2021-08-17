package ca.ntro.backend;

import ca.ntro.messages.NtroMessage;
import ca.ntro.services.ModelStoreSync;

public abstract class BackendMessageHandler<M extends NtroMessage> {
	
	public abstract void handleNow(ModelStoreSync modelStore, M message) throws BackendError ;
	public abstract void handleLater(ModelStoreSync modelStore, M message) throws BackendError;

}
