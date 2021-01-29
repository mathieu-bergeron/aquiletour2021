package ca.ntro.messages;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class MessageFactory {
	
	private static MessageReceptors messageReceptors = new MessageReceptors();
	
	public static <M extends NtroMessage> M getOutgoingMessage(Class<M> messageClass) {
		T.call(MessageFactory.class);
		
		M message = Factory.newInstance(messageClass);

		return message;
	}
	
	public static void addMessageReceptor(Class<? extends NtroMessage> messageClass, 
			                              MessageReceptor messageReceptionTask) {
		T.call(MessageFactory.class);
		
		messageReceptors.addReceptor(messageClass, messageReceptionTask);
	}

	static void sendMessage(NtroMessage message) {
		T.call(MessageFactory.class);
		
		messageReceptors.sendMessage(message);
	}
	
	
	
	
	
	

}
