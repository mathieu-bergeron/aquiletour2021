package ca.ntro.messages;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class MessageFactory {

	private static Map<Class<? extends NtroMessage>, NtroMessage> messages = new HashMap<>();

	private static MessageHandlers handlers = new MessageHandlers();
	private static MessageHandlerTasks tasks = new MessageHandlerTasks();

	public static <M extends NtroMessage> M getOutgoingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		// FIXME: this assumes that getIncomingMessage is called before
		M message = (M) messages.get(messageClass);

		if(message == null) {
			System.err.println("No receptor for: " +  Ntro.introspector().getSimpleNameForClass(messageClass));
			//MustNot.beNull(message);
			message = Factory.newInstance(messageClass);
		}

		return message;
	}

	public static <M extends NtroMessage> MessageHandler<M> createMessageHandler(Class<M> messageClass, Class<MessageHandler<M>> handlerClass) {
		T.call(MessageFactory.class);

		return null;
	}

	public static <M extends NtroMessage> MessageHandlerTask createMessageHandlerTask(Class<M> messageClass) {
		T.call(MessageFactory.class);

		return null;
	}


	public static <M extends NtroMessage> M getIncomingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		M message = Factory.newInstance(messageClass);

		messages.put(messageClass, message);

		return message;
	}


	public static void reset() {
		T.call(MessageFactory.class);
		
		messages = new HashMap<>();
	}
}
