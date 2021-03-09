package ca.ntro.jsweet.services;

import java.util.function.Function;

import ca.ntro.core.Ntro;
import ca.ntro.core.services.BackendService;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import def.dom.MessageEvent;
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
		T.call(this);

		webSocket.send(Ntro.jsonService().toString(message));
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		T.call(this);

		webSocket.onmessage = new Function<MessageEvent, Object>() {
			@Override
			public Object apply(MessageEvent t) {

				M message = Ntro.jsonService().fromString(messageClass, t.data.toString());
				handler.handle(message);
				
				return null;
			}
		};
		
	}

}
