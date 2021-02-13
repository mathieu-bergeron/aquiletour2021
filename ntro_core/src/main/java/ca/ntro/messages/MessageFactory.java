package ca.ntro.messages;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class MessageFactory {

	private static Map<Class<? extends NtroMessage>, NtroMessage> messages = new HashMap<>();


	public static <M extends NtroMessage> M getOutgoingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		// FIXME: this assumes that getIncomingMessage is called before
		M message = (M) messages.get(messageClass);

		if(message == null) {
			System.out.println(Ntro.introspector().getSimpleNameForClass(messageClass));
			MustNot.beNull(message);
		}

		return message;
	}


	public static <M extends NtroMessage> M getIncomingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);

		M message = Factory.newInstance(messageClass);

		messages.put(messageClass, message);

		return message;
	}
}
