package ca.ntro.jsweet.services;

import java.util.HashMap;
import java.util.Map;
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
	
	private final Map<Class<? extends NtroMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	public BackendServiceJSweet(String connectionString) {
		super();
		T.call(this);
		
		webSocket = new WebSocket(connectionString);

		webSocket.onmessage = new Function<MessageEvent, Object>() {
			@Override
			public Object apply(MessageEvent t) {

				NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, t.data.toString());
				
				MessageHandler<?> handler = handlers.get(message.getClass());
				
				if(handler != null) {
					
					handler.handleUntyped(message);
				}

				return null;
			}
		};
	}

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		T.call(this);

		webSocket.send(Ntro.jsonService().toString(message));
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		T.call(this);

		handlers.put(messageClass, handler);
	}

}
