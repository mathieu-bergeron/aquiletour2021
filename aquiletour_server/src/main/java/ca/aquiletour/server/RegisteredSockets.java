package ca.aquiletour.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

import ca.ntro.core.NtroUser;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class RegisteredSockets {

	// FIXME: more than one user will observe!!!!!
	private static Map<DocumentPath, NtroUser> modelObservators = new HashMap<>();
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
		modelObservators.put(documentPath, user);
	}
	
	public static NtroUser getUserThatObservesModel(DocumentPath documentPath) {
		return modelObservators.get(documentPath);
	}
}
