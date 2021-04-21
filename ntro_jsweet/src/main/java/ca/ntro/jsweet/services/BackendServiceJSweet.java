package ca.ntro.jsweet.services;

import java.util.HashMap;

import java.util.Map;

import ca.ntro.core.system.trace.__T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.services.BackendService;
import ca.ntro.services.Ntro;
import def.dom.WebSocket;
import static def.dom.Globals.window;

public class BackendServiceJSweet extends BackendService {
	
	private final WebSocket webSocket;
	
	private final Map<Class<? extends NtroMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	public BackendServiceJSweet() {
		super();
		__T.call(this, "<init>");

		String protocol = "ws";
		if(window.location.protocol.contains("https")) {
			protocol = "wss";
		}

		String connectionString = protocol + "://" + window.location.host + ca.ntro.core.Constants.MESSAGES_URL_PATH_SOCKET;

		webSocket = new WebSocket(connectionString);

		webSocket.onmessage = t -> {

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, t.data.toString());
			
			MessageHandler<?> handler = handlers.get(message.getClass());
			
			if(handler != null) {
				
				handler.handleUntyped(message);
			}

			return null;
		};
		
		webSocket.onopen = t -> {

			// FIXME: there is no guarantee  that MessageFactory.registerUser has been called
			//        we must use initialization tasks
			NtroRegisterSocketMessage registerSocketNtroMessage = Ntro.messages().create(NtroRegisterSocketMessage.class);

			sendMessageToBackend(registerSocketNtroMessage);
			
			return null;
		};
	}

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		__T.call(this, "sendMessageToBackend");
		
		webSocket.send(Ntro.jsonService().toString(message));
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		__T.call(this, "handleMessageFromBackend");

		handlers.put(messageClass, handler);
	}

	@Override
	public <MSG extends NtroMessage> boolean handlerExistsFor(MSG message) {
		// Always true: all messages are sent on the socket
		return true;
	}

}
