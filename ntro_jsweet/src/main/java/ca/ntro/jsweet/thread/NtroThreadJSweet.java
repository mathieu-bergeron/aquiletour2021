package ca.ntro.jsweet.thread;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public class NtroThreadJSweet implements NtroThread {
	
	private long threadId;

	public NtroThreadJSweet(long threadId) {
		this.threadId = threadId;
	}

	@Override
	public void sendMessageToThread(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
