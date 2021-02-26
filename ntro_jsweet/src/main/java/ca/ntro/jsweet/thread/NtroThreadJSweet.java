package ca.ntro.jsweet.thread;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public class NtroThreadJSweet implements NtroThread {

	@Override
	public void sendMessageToThread(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
