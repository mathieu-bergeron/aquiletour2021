package ca.ntro;

import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.messages.NtroMessage;

public abstract class BackendMessageHandler<M extends NtroMessage> {

	public abstract void handle(ModelStoreSync modelStore, M message);
}
