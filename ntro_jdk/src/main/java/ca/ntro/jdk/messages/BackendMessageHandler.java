package ca.ntro.jdk.messages;

import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.messages.NtroMessage;

public abstract class BackendMessageHandler<M extends NtroMessage> {

	public abstract void handle(ModelStoreSync modelStore, M message);
}
