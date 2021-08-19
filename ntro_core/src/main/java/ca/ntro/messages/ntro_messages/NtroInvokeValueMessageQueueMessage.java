package ca.ntro.messages.ntro_messages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class NtroInvokeValueMessageQueueMessage extends NtroMessage {
	
	private List<NtroInvokeValueMethodMessage> invokeValueMethodMessages = new ArrayList<>();
	private Set<String> observerTokens = new HashSet<>();

	public List<NtroInvokeValueMethodMessage> getInvokeValueMethodMessages() {
		return invokeValueMethodMessages;
	}

	public void setInvokeValueMethodMessages(List<NtroInvokeValueMethodMessage> invokeValueMethodMessages) {
		this.invokeValueMethodMessages = invokeValueMethodMessages;
	}

	public void addInvokeValueMessage(NtroInvokeValueMethodMessage message) {
		T.call(this);

		getInvokeValueMethodMessages().add(message);
	}

	public void addObservers(Set<String> observerTokens) {
		T.call(this);
		
		observerTokens().addAll(observerTokens);
	}

	public Set<String> observerTokens() {
		T.call(this);

		return observerTokens;
	}
}
