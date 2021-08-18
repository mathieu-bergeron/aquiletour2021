package ca.ntro.jsweet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.services.BackendService;
import ca.ntro.services.Ntro;
import def.dom.WebSocket;
import static def.dom.Globals.window;

public class BackendServiceJSweet extends BackendService {
	
	// FIXME: would be cleaner wait for
	//        WebSocket to be open using an initialization task
	private boolean isOpen = false;
	private final List<NtroMessage> queuedMessages = new ArrayList<>();
	
	private WebSocket webSocket;
	
	private final Map<Class<? extends NtroMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	private final String connectionString;
	
	public BackendServiceJSweet() {
		super();
		__T.call(this, "<init>");

		String protocol = "ws";
		if(window.location.protocol.contains("https")) {
			protocol = "wss";
		}

		connectionString = protocol + "://" + window.location.host + ca.ntro.core.Constants.MESSAGES_URL_PATH_SOCKET;

		connectWebSocket(Ntro.currentUser().getAuthToken());
	}

	private void connectWebSocket(String authToken) {
		T.call(this);

		webSocket = new WebSocket(connectionString);

		webSocket.onmessage = t -> {
			
			System.out.println(t.data.toString());

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, t.data.toString());

			MessageHandler<?> handler = handlers.get(message.getClass());
			
			if(handler != null) {
				
				handler.handleUntyped(message);
			}

			return null;
		};
		
		webSocket.onopen = t -> {
			
			registerWebSocket(authToken);
			
			isOpen = true;
			for(NtroMessage queuedMessage : queuedMessages) {
				sendMessageToBackend(queuedMessage);
			}
			
			return null;
		};
	}

	private void registerWebSocket(String authToken) {
		T.call(this);

		NtroRegisterSocketMessage registerSocketNtroMessage = Ntro.messages().create(NtroRegisterSocketMessage.class);
		registerSocketNtroMessage.setAuthToken(authToken);

		sendMessageToBackend(registerSocketNtroMessage);
	}

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		__T.call(this, "sendMessageToBackend");
		
		if(isOpen) {
			webSocket.send(Ntro.jsonService().toString(message));
		}else {
			queuedMessages.add(message);
		}
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

	@Override
	public void sendMessageToBackendWithExceptions(NtroMessage message) throws BackendError {
		T.call(this);
		// XXX: not supported in JSweet
	}

}
