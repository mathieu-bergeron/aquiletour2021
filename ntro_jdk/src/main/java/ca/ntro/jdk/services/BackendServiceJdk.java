package ca.ntro.jdk.services;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.BackendService;

public class BackendServiceJdk extends BackendService {

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		throw new RuntimeException("TODO");
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		throw new RuntimeException("TODO");
	}

	@Override
	public <MSG extends NtroMessage> boolean handlerExistsFor(MSG message) {
		throw new RuntimeException("TODO");
	}

}
