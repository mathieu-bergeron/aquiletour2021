package ca.aquiletour.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.InvokeValueMethodNtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;

public class RegisteredSockets {

	// FIXME: much simple if every Session registers a Socket (by authToken)
	//        then no need to change socket on login (the session authToken is specific to the browser, not the user)
	//        still we need to change authToken if user changes

	private static Map<DocumentPath, Set<NtroUser>> modelObservators = new HashMap<>();
	private static Map<NtroUser, Set<Session>> userSockets = new HashMap<>();
	
	public static void registerUserSocket(NtroUser user, Session socket) {
		Set<Session> sockets = userSockets.get(user);
		
		if(sockets == null) {
			sockets = new HashSet<>();
			userSockets.put(user, sockets);
		}

		sockets.add(socket);
		
		System.out.println("registered user socket for " + user.getId());
	}

	public static void deregisterSocket(Session socket) {
		for(Map.Entry<NtroUser, Set<Session>> entry : userSockets.entrySet()) {
			boolean removed = entry.getValue().remove(socket);
			if(removed) {
				System.out.println("deregistered socket for " + entry.getKey().getId());
			}
		}
	}

	public static void sendMessageToUserSockets(NtroUser user, NtroMessage message) throws IOException {
		Set<Session> sockets = userSockets.get(user);
		
		if(sockets != null) {
			for(Session socket : sockets) {
				if(socket.isOpen()) {
					socket.getRemote().sendString(Ntro.jsonService().toString(message));
				}
			}
		}
	}

	public static void registerThatUserObservesModel(NtroUser user, DocumentPath documentPath) {
		Set<NtroUser> observators = modelObservators.get(documentPath);
		
		if(observators == null) {
			observators = new HashSet<>();
			modelObservators.put(documentPath, observators);
		}
		
		observators.add(user);
	}
	
	public static void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(RegisteredSockets.class);
		
		if(args.size() > 0) {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName + " " + args.get(0));
		}else {
			System.out.println("onValueMethodInvoked: " + valuePath + " " + methodName);
		}
		
		Set<NtroUser> observers = modelObservators.get(valuePath.getDocumentPath());

		if(observers != null) {

			for(NtroUser observer : observers) {

				InvokeValueMethodNtroMessage message = Ntro.messages().create(InvokeValueMethodNtroMessage.class);
				message.setValuePath(valuePath);
				message.setMethodName(methodName);
				message.setArgs(args);
				
				try {
					
					RegisteredSockets.sendMessageToUserSockets(observer, message);

				} catch (IOException e) {

					Log.fatalError("Unable to send message to user " + observer.getId(), e);
				}
			}
		}
	}
}
