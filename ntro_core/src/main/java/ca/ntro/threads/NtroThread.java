package ca.ntro.threads;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public interface NtroThread {
	
	void sendMessageToThread(NtroMessage message);
	<M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler);

	// Ntro.threads().sendMessageToParentThread(NtroMessage message);
	// Ntro.threads().handleMessageFromParentThread(Class<M> messageClass, MessageHandler<M> handler);
	
	// NtroThread t = Ntro.threads().createThread();
	
	// Ntro.threads().executeLater(NtroTask task);  // executes as soon as message queue is empty
	
	// Ntro.threads().isCurrentThreadRoot();
	
	// Ntro.messages().sendMessage(NtroMessage message);  
	// sends to currentThread().messageReceptors
	//       if no receptors, send to parent thread
	//       if no parent thread, send to backend
	
	// Ntro.backend().sendMessage(NtroMessage message);
	// on server: sends directly to backend thread
	// on client: sends to WebSocket connection
}
