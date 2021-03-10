package ca.aquiletour.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

import ca.ntro.core.Ntro;
import ca.ntro.core.NtroUser;
import ca.ntro.messages.NtroMessage;

public class RegisteredSockets {
	
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
}
