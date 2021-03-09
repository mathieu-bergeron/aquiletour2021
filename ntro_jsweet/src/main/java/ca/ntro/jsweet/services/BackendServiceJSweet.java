package ca.ntro.jsweet.services;

import ca.ntro.core.Ntro;
import ca.ntro.core.services.BackendService;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import def.dom.WebSocket;

public class BackendServiceJSweet extends BackendService {
	
	private final WebSocket webSocket;
	
	public BackendServiceJSweet(String connectionString) {
		super();
		T.call(this);
		
		webSocket = new WebSocket(connectionString);
	}

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		webSocket.send(Ntro.jsonService().toString(message));
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
