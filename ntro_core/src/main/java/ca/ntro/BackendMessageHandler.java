package ca.ntro;

import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.messages.NtroMessage;

public abstract class BackendMessageHandler<M extends NtroMessage> {

	public abstract void handleNow(ModelStoreSync modelStore, M message);
	public abstract void handleLater(ModelStoreSync modelStore, M message);

}
