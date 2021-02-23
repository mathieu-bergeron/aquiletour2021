package ca.ntro.threads;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public interface NtroThread {
	
	void sendMessageToThread(NtroMessage message);
	<M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler);

	// Ntro.threads().sendMessageToParentThread(NtroMessage message);
	// Ntro.threads().handleMessageFromParentThread(Class<M> messageClass, MessageHandler<M> handler);
	
	// NtroThread t = Ntro.threads().createThread();
	
	// Ntro.threads().runLater(NtroTask task);
	
	// Ntro.threads().isCurrentThreadRoot();

}
