package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.MessageHandlerTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.NtroUserMessage;
import ca.ntro.threads.NtroThread;

public abstract class MessageService {
	
	// FIXME: only one handler by message???
	private Map<Class<? extends NtroMessage>, MessageHandler<?>> handlers = new HashMap<>();
	
	public <M extends NtroMessage> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
		
		handlers.put(messageClass, handler);
		
		for(NtroThread subThread : Ntro.threadService().subThreads()) {
			subThread.handleMessageFromThread(messageClass, handler);
		}

		Ntro.backendService().handleMessageFromBackend(messageClass, handler);
	}

	public void registerHandlerTask(Class<? extends NtroMessage> messageClass, MessageHandlerTask messageHandlerTask) {
		// JSWEET: compilation error with <MSG extends NtroMessage>
		handlers.put(messageClass, new MessageHandler() {
			@Override
			public void handle(NtroMessage message) {
				messageHandlerTask.setMessage(message);
				messageHandlerTask.triggerHandlerOnce();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <M extends NtroMessage> void send(M message) {
		if(handlers.containsKey(message.getClass())) {

			MessageHandler<M> handler = (MessageHandler<M>) handlers.get(message.getClass());
			handler.handle(message);

		} else if(Ntro.threadService().hasParentThread()) {
			
			Ntro.threadService().sendMessageToParentThread(message);

		}else {

			Ntro.backendService().sendMessageToBackend(message);
		}
	}

	void reset() {
		T.call(this);
		
		handlers = new HashMap<>();
	}

	public <MSG extends NtroMessage> MSG create(Class<MSG> messageClass) {
		T.call(this);

		MSG message = Ntro.factory().newInstance(messageClass);
		
		if(message instanceof NtroUserMessage) {
			((NtroUserMessage) message).setUser(Ntro.userService().user());
		}

		return message;
	}

	public <M extends NtroMessage> MessageHandler<M> createMessageHandler(Class<M> messageClass, Class<MessageHandler<M>> handlerClass) {
		T.call(this);

		return null;
	}

	public  <M extends NtroMessage> MessageHandlerTask createMessageHandlerTask(Class<M> messageClass) {
		T.call(this);
		
		MessageHandlerTask handlerTask = new MessageHandlerTask();

		handlerTask.setTaskId(Ntro.introspector().getSimpleNameForClass(messageClass));

		Ntro.messages().registerHandlerTask(messageClass, handlerTask);

		return handlerTask;
	}
}
