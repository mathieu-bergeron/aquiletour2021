package ca.aquiletour.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.InvokeValueMethodNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;

public class RegisteredSockets {


	// For each model, the sessions we need to update when the model changes
	// (a session is represented by its authToken)
	private static Map<DocumentPath, Set<String>> modelObservers = new HashMap<>();

	// The socket of each session, if it exists
	// (a session is represented by its authToken)
	// (the Session type here is a jetty WebSocket)
	private static BiMap<String, Session> sockets = HashBiMap.create();
	
	public static void registerUserSocket(NtroUser user, Session socket) {
		sockets.put(user.getAuthToken(), socket);
		
		System.out.println("registered user socket for " + user.getId() + " " + user.getAuthToken());
	}

	public static void deregisterSocket(Session socket) {
		String authToken = sockets.inverse().get(socket);
		Session removed = sockets.remove(authToken);
		if(removed != null) {
			System.out.println("deregistered socket for " + authToken);
		}
	}

	public static void sendMessageToUser(NtroUser user, NtroMessage message) throws IOException {
		sendMessageToUser(user.getAuthToken(), message);
	}

	public static void sendMessageToUser(String authToken, NtroMessage message) throws IOException {
		Session socket = sockets.get(authToken);

		if(socket != null) {
			if(socket.isOpen()) {
				socket.getRemote().sendString(Ntro.jsonService().toString(message));
			}
		}
	}

	public static void registerModelObserver(NtroUser user, DocumentPath documentPath) {
		Set<String> observers = modelObservers.get(documentPath);
		
		if(observers == null) {
			observers = new HashSet<>();
			modelObservers.put(documentPath, observers);
		}

		observers.add(user.getAuthToken());
	}
	
	public static void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(RegisteredSockets.class);
		
		if(args.size() > 0) {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));
		}else {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName);
		}
		
		Set<String> observers = (Set<String>) modelObservers.get(valuePath.getDocumentPath());

		if(observers != null) {

			for(String authToken : observers) {

				InvokeValueMethodNtroMessage message = Ntro.messages().create(InvokeValueMethodNtroMessage.class);
				message.setValuePath(valuePath);
				message.setMethodName(methodName);
				message.setArgs(args);
				
				try {
					
					RegisteredSockets.sendMessageToUser(authToken, message);

				} catch (IOException e) {

					Log.fatalError("Unable to send message to user " + authToken, e);
				}
			}
		}
	}
}
