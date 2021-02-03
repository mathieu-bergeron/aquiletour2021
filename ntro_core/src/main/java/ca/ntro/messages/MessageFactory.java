package ca.ntro.messages;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class MessageFactory {
	
	private static Map<Class<? extends NtroMessage>, NtroMessage> messages = new HashMap<>();
	
	
	public static <M extends NtroMessage> M getOutgoingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		return getMessage(messageClass);
	}

	private static <M extends NtroMessage> M getMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		M message = (M) messages.get(messageClass);
		
		if(message == null) {
			message = Factory.newInstance(messageClass);
			messages.put(messageClass, message);
		}

		return message;
	}

	public static <M extends NtroMessage> M getIncomingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		M message = getMessage(messageClass);
		
		message.reset();
		
		return message;
	}
}
