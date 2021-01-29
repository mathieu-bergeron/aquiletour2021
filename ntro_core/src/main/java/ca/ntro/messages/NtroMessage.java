package ca.ntro.messages;

import ca.ntro.core.system.trace.T;

public abstract class NtroMessage {
	
	
	public void sendMessage() {
		T.call(this);
		
		MessageFactory.sendMessage(this);
	}

}
