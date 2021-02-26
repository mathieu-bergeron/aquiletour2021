package ca.ntro.jdk.services;

import ca.ntro.core.services.BackendService;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public class BackendServiceJSweet extends BackendService {

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
