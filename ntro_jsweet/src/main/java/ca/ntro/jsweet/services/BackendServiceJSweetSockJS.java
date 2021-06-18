package ca.ntro.jsweet.services;


import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.JSweetGlobals.SockJS;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.messages.ntro_messages.UpdateSocketStatusMessage;
import ca.ntro.services.BackendService;
import ca.ntro.services.Ntro;
import def.sockjs.Globals;


import static def.dom.Globals.window;

public class BackendServiceJSweetSockJS extends BackendService {
	
	// FIXME: would be cleaner wait for
	//        WebSocket to be open using an initialization task
	private boolean isOpen = false;
	
	private SockJS sockJS;
	
	private final Map<Class<? extends NtroMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	private final java.lang.String connectionString;
	
	public BackendServiceJSweetSockJS() {
		super();
		__T.call(this, "<init>");

		String protocol = window.location.protocol;

		connectionString = protocol + "//" + window.location.host + ca.ntro.core.Constants.MESSAGES_URL_PATH_SOCKET;

		connectWebSocket(Ntro.currentUser().getAuthToken());
	}

	private void connectWebSocket(String authToken) {
		T.call(this);

		sockJS = Globals.newSockJS(connectionString);
		
		sockJS.onmessage = t -> {
			
			
			System.out.println(t.data.toString());

			NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, t.data.toString());

			MessageHandler<?> handler = handlers.get(message.getClass());
			
			if(handler != null) {
				
				handler.handleUntyped(message);
			}

			return null;
		};
		
		sockJS.onopen = t -> {
				
			System.out.println("sockJS: open");

			isOpen = true;
			
			registerWebSocket(authToken);
			
			UpdateSocketStatusMessage updateSocketStatusMessage = Ntro.messages().create(UpdateSocketStatusMessage.class);
			updateSocketStatusMessage.setSocketOpen(isOpen);
			Ntro.messages().send(updateSocketStatusMessage);
			
			return null;
		};

		sockJS.onclose = t -> {
				
			System.out.println("sockJS: close");

			isOpen = false;

			UpdateSocketStatusMessage updateSocketStatusMessage = Ntro.messages().create(UpdateSocketStatusMessage.class);
			updateSocketStatusMessage.setSocketOpen(isOpen);
			Ntro.messages().send(updateSocketStatusMessage);
			
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
			sockJS.send(Ntro.jsonService().toString(message));
		}else {
			Log.info("[sendMessageToBackend] socket closed");
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

}
