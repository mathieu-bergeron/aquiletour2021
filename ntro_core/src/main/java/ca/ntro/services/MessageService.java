package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

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

	public void registerHandlerTask(Class<? extends NtroMessage> messageClass, MessageHandlerTask<NtroMessage> messageHandlerTask) {
		T.call(this);
		
		// JSWEET: compilation error with <MSG extends NtroMessage>
		handlers.put(messageClass, new MessageHandler<NtroMessage>() {
			@Override
			public void handle(NtroMessage message) {
				messageHandlerTask.setMessage(message);
				messageHandlerTask.triggerHandlerOnce();
			}
		});
	}
	
	protected <M extends NtroMessage> boolean handlerExistsFor(M message) {
		T.call(this);
		
		return handlers.containsKey(message.getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <M extends NtroMessage> void send(M message) {
		T.call(this);
		
		// XXX: user might have changed after message creation
		//      e.g. in the case of a login
		if(message instanceof NtroUserMessage) {
			((NtroUserMessage) message).setUser(Ntro.currentUser());
		}
		
		if(handlerExistsFor(message)) {

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <MSG extends NtroMessage> MSG create(Class<MSG> messageClass) {
		T.call(this);

		MSG message = Ntro.factory().newInstance(messageClass);

		if(message instanceof NtroUserMessage) {
			((NtroUserMessage) message).setUser(Ntro.currentUser());
		}

		return message;
	}

	public <M extends NtroMessage> MessageHandler<M> createMessageHandler(Class<M> messageClass, Class<MessageHandler<M>> handlerClass) {
		T.call(this);

		return null;
	}

	public  <M extends NtroMessage> MessageHandlerTask<NtroMessage> createMessageHandlerTask(Class<M> messageClass) {
		T.call(this);
		
		MessageHandlerTask<NtroMessage> handlerTask = new MessageHandlerTask<NtroMessage>();

		handlerTask.setTaskId(Ntro.introspector().getSimpleNameForClass(messageClass));

		Ntro.messages().registerHandlerTask(messageClass, handlerTask);

		return handlerTask;
	}
	
	public void sendQueuedMessages() {
		// XXX: must be redefined on the server
	}
}
