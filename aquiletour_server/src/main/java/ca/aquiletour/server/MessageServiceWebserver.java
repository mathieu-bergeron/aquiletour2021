package ca.aquiletour.server;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.MessageServiceJdk;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class MessageServiceWebserver extends MessageServiceJdk {

	@SuppressWarnings("unchecked")
	public <M extends NtroMessage> void send(M message) {
		T.call(this);

		super.send(message);
		
		if(!localHandlerPresent(message)) {

			System.out.println("sending on WebSocket: " + message.getClass().getSimpleName());
			RegisteredSockets.sendMessageToUser(Ntro.currentUser(), message);
		}
	}
}
