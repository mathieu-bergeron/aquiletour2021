package ca.ntro.threads;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public interface NtroThread {
	
	String getThreadId();
	void sendMessageToThread(NtroMessage message);
	<M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler);

	// Ntro.threadService().sendMessageToParentThread(NtroMessage message);
	// Ntro.threadService().handleMessageFromParentThread(Class<M> messageClass, MessageHandler<M> handler);
	
	// NtroThread t = Ntro.threads().createThread();
	
	// Ntro.threadService().executeLater(NtroTask task);  // executes as soon as message queue is empty
	
	// Ntro.threadService().isCurrentThreadRoot();
	
	// Ntro.messageService().sendMessage(NtroMessage message);  
	// sends to currentThread().messageReceptors
	//       if no receptors, send to parent thread
	//       if no parent thread, send to backend
	
	// NtroMessageHandler handler = Ntro.messageService().getMessageHandler(Class<M> classMessage)
	// NtroMessageHandlerTask handlerTask = Ntro.messageService().getMessageHandlerTask(Class<M> classMessage)
	
	// Ntro.backendService().sendMessage(NtroMessage message);
	// on server: sends directly to backend thread
	// on client: sends to WebSocket connection
}
