package ca.ntro.messages;


import ca.ntro.core.Ntro;
import ca.ntro.core.NtroUser;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class MessageFactory {

	private static NtroUser currentUser;

	public static void registerCurrentUser(NtroUser currentUser) {
		MessageFactory.currentUser = currentUser;
	}

	public static <MSG extends NtroMessage> MSG createMessage(Class<MSG> messageClass) {
		T.call(MessageFactory.class);

		MSG message = Factory.newInstance(messageClass);
		
		if(message instanceof NtroUserMessage) {
			((NtroUserMessage) message).setUser(currentUser);
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
