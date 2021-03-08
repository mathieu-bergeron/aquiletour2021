package ca.ntro.jdk.services;

import ca.ntro.core.services.BackendService;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public class BackendServiceJdk extends BackendService {

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		throw new RuntimeException("TODO");
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		throw new RuntimeException("TODO");
	}

}
