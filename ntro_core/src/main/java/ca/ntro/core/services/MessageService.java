package ca.ntro.core.services;

import ca.ntro.core.Ntro;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public abstract class MessageService {
	
	public <M extends NtroMessage> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO: this creates an handler inside the currentThread
		
		// XXX: we also handle messages coming from subthread
		for(NtroThread subThread : Ntro.threadService().subThreads()) {
			subThread.handleMessageFromThread(messageClass, handler);
		}

		// XXX: we also handle messages coming from backend
		Ntro.backendService().handleMessageFromBackend(messageClass, handler);
	}

	public <M extends NtroMessage> void registerHandlerTask(Class<M> messageClass, MessageHandlerTask task) {
		
		// TODO: create an anon handler that launches the MessageHandlerTask when message is treated

	}
	
	public <M extends NtroMessage> void sendMessage(M message) {
		// XXX: if we have a handler inside this thread, use it
		Boolean TODO = null;
		if(TODO) {

		} else if(Ntro.threadService().hasParentThread()) {
			
			Ntro.threadService().sendMessageToParentThread(message);

		}else {

			Ntro.backendService().sendMessageToBackend(message);
		}
	}
}
