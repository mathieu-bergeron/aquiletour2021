package ca.ntro.core.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public abstract class MessageService {
	
	private Map<Class<? extends NtroMessage>, MessageHandler> handlers = new HashMap<>();
	
	public <M extends NtroMessage> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
		
		handlers.put(messageClass, handler);
		
		// XXX: we also handle messages coming from subthread
		for(NtroThread subThread : Ntro.threadService().subThreads()) {
			subThread.handleMessageFromThread(messageClass, handler);
		}

		// XXX: we also handle messages coming from backend
		Ntro.backendService().handleMessageFromBackend(messageClass, handler);
	}

	public <M extends NtroMessage> void registerHandlerTask(Class<M> messageClass, NtroTask task) {
		handlers.put(messageClass, new MessageHandler<M>() {
			@Override
			public void handle(M message) {
				task.notifyExitTaskFinished();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroMessage> void sendMessage(M message) {
		// XXX: if we have a handler inside this thread, use it

		if(handlers.containsKey(message.getClass())) {
			
			MessageHandler<M> handler = (MessageHandler<M>) handlers.get(message.getClass());
			handler.handle(message);

		} else if(Ntro.threadService().hasParentThread()) {
			
			Ntro.threadService().sendMessageToParentThread(message);

		}else {

			Ntro.backendService().sendMessageToBackend(message);
		}
	}
}
