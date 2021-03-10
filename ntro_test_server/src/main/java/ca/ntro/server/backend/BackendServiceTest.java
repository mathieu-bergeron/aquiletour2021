package ca.ntro.server.backend;

import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public class BackendServiceTest extends BackendServiceServer  {
	
	@Override
	public void sendMessageToBackend(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addBackendMessageHandlers() {
		// TODO Auto-generated method stub
		
	}

}
