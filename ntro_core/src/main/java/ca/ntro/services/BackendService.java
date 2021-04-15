package ca.ntro.services;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public abstract class BackendService {

	public abstract void sendMessageToBackend(NtroMessage message);
	public abstract <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler);
	public abstract <MSG extends NtroMessage> boolean handlerExistsFor(MSG message);

}
