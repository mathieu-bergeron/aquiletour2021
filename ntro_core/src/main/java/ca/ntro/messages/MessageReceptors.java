package ca.ntro.messages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class MessageReceptors {

	private static Map<Class<? extends NtroMessage>, Set<MessageHandler<?>>> messageReceptors = new HashMap<>();

	public void addReceptor(Class<? extends NtroMessage> messageClass, MessageHandler<?> messageReceptionTask) {
		T.call(this);

		Set<MessageHandler<?>> receptorSet = messageReceptors.get(messageClass);

		if(receptorSet == null) {
			receptorSet = new HashSet<>();
		}

		// FIXME: limiting to a single receptor for now
		receptorSet.clear();

		receptorSet.add(messageReceptionTask);

		messageReceptors.put(messageClass, receptorSet);
	}

	public void sendMessage(NtroMessage message) {
		T.call(this);

		Set<MessageHandler<?>> receptorSet = messageReceptors.get(message.getClass());

		if(receptorSet != null) {

			sendMessage(message, receptorSet);

		}else {
			System.out.println("[WARNING] no receptor for message " + Ntro.introspector().getSimpleNameForClass(message.getClass()));
		}
	}

	private void sendMessage(NtroMessage message, Set<MessageHandler<?>> receptorSet) {
		T.call(this);

		/*
		for(MessageHandler<?> receptor : receptorSet) {
			receptor.resetTask();
			receptor.setMessage(message);
			receptor.execute();
		}
		*/
	}
}
