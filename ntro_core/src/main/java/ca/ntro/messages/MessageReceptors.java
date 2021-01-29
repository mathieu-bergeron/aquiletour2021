package ca.ntro.messages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.T;

public class MessageReceptors {

	private static Map<Class<? extends NtroMessage>, Set<MessageReceptor>> messageReceptors = new HashMap<>();

	public void addReceptor(Class<? extends NtroMessage> messageClass, MessageReceptor messageReceptionTask) {
		T.call(this);
		
		Set<MessageReceptor> receptorSet = messageReceptors.get(messageClass);
		
		if(receptorSet == null) {
			receptorSet = new HashSet<>();
		}
		
		receptorSet.add(messageReceptionTask);
		
		messageReceptors.put(messageClass, receptorSet);
	}

	public void sendMessage(NtroMessage message) {
		T.call(this);

		Set<MessageReceptor> receptorSet = messageReceptors.get(message.getClass());
		
		if(receptorSet != null) {
			
			sendMessage(message, receptorSet);

		}else {
			System.out.println("[WARNING] no receptor for message " + message.getClass().getSimpleName());
		}
	}

	private void sendMessage(NtroMessage message, Set<MessageReceptor> receptorSet) {
		T.call(this);
		
		for(MessageReceptor receptor : receptorSet) {
			receptor.reset();
			receptor.setMessage(message);
			receptor.execute();
		}
	}
	

}
