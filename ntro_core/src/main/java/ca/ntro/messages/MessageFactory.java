package ca.ntro.messages;


import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class MessageFactory {

	public static <MSG extends NtroMessage> MSG createMessage(Class<MSG> messageClass) {
		T.call(MessageFactory.class);

		MSG message = Factory.newInstance(messageClass);
		
		if(message instanceof NtroUserMessage) {
			((NtroUserMessage) message).setUser(Ntro.userService().currentUser());
		}

		return message;
	}

	public static <M extends NtroMessage> MessageHandler<M> createMessageHandler(Class<M> messageClass, Class<MessageHandler<M>> handlerClass) {
		T.call(MessageFactory.class);

		return null;
	}

	public static <M extends NtroMessage> MessageHandlerTask createMessageHandlerTask(Class<M> messageClass) {
		T.call(MessageFactory.class);
		
		MessageHandlerTask handlerTask = new MessageHandlerTask();

		handlerTask.setTaskId(Ntro.introspector().getSimpleNameForClass(messageClass));

		Ntro.messageService().registerHandlerTask(messageClass, handlerTask);

		return handlerTask;
	}
}
