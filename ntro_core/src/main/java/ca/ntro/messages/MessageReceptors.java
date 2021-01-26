package ca.ntro.messages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.T;

public class MessageReceptors {

	private static Map<Class<? extends Message>, Set<MessageReceptionTask>> messageReceptors = new HashMap<>();

	public void addReceptor(Class<? extends Message> messageClass, MessageReceptionTask messageReceptionTask) {
		T.call(this);
		
		Set<MessageReceptionTask> receptorSet = messageReceptors.get(messageClass);
		
		if(receptorSet == null) {
			receptorSet = new HashSet<>();
		}
		
		receptorSet.add(messageReceptionTask);
	}

	public void sendMessage(Message message) {
		T.call(this);

		Set<MessageReceptionTask> receptorSet = messageReceptors.get(message.getClass());
		
		if(receptorSet != null) {
			
			sendMessage(message, receptorSet);

		}else {
			System.out.println("[WARNING] no receptor for message " + message.getClass().getSimpleName());
		}
	}

	private void sendMessage(Message message, Set<MessageReceptionTask> receptorSet) {
		T.call(this);
		
		for(MessageReceptionTask receptor : receptorSet) {
			receptor.reset();
			receptor.setMessage(message);
			receptor.execute();
		}
	}
	

}
