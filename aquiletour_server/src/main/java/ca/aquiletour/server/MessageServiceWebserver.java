package ca.aquiletour.server;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.MessageServiceJdk;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class MessageServiceWebserver extends MessageServiceJdk {
	
	private List<NtroMessage> messageQueue = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public <M extends NtroMessage> void send(M message) {
		T.call(this);
		
		if(ifHandlerExists(message)) {

			super.send(message);

		}else {

			System.out.println("saving message " + message.getClass().getSimpleName());
			messageQueue.add(message);
		}
	}

	private <M extends NtroMessage> boolean ifHandlerExists(M message) {
		T.call(this);

		return handlerExistsFor(message) || Ntro.backendService().handlerExistsFor(message);
	}

	@Override
	public void sendQueuedMessages() {
		T.call(this);
		
		for(NtroMessage message : messageQueue) {

			if(ifHandlerExists(message)) {
				
				super.send(message);

			}else {
				
				System.out.println("sending message to WebSocket " + message.getClass().getSimpleName());
				RegisteredSockets.sendMessageToUser(Ntro.currentUser(), message);
			}
		}

		messageQueue.clear();
	}
}
